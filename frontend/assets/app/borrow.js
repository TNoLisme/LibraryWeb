const tableData = document.querySelector("#tableData");
const borrowDate = document.querySelector("#borrowDate");
const returnDate = document.querySelector("#returnDate");
const statusBook = document.querySelector("#status");
const bookID = document.querySelector("#bookID");
const memID = document.querySelector("#memID");

const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/borrowrequests";
const PATH_BOOK = BASE_URL + "/api/books";
const PATH_MEMBER = BASE_URL + "/api/members";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
  renderBooksList();
});
const statusFilterElement = document.querySelector("#statusFilter");

statusFilterElement.addEventListener("change", (event) => {
  const statusFilterValue = event.target.value;
  renderTable(statusFilterValue); // Lọc bảng theo trạng thái đã chọn
});




const renderTable = async (statusFilter = "") => {
  try {
    const data = await axios.get(PATH);  // Gọi API để lấy dữ liệu
    items = data?.data || [];  // Lấy dữ liệu hoặc mảng rỗng nếu không có dữ liệu

    // Kiểm tra và lọc dữ liệu theo trạng thái nếu có bộ lọc
    if (statusFilter) {
      items = items.filter(item => item.status === statusFilter);
    }

    // Kiểm tra nếu không có dữ liệu sau khi lọc
    if (items.length === 0) {
      document.querySelector("#tableData tbody").innerHTML = "<tr><td colspan='5' style='text-align: center;'>No data available</td></tr>";
      return;
    }

    // Tạo HTML cho bảng
    const rows = items
      .map(
        (item) => `  
      <tr data-id="${item.id}">
        <td>${item.bookTitle}</td>
        <td>${item.memberFullName}</td>
        <td>${formatDate(item.borrowDate)}</td>
        <td>${item.status}</td> <!-- Hiển thị Trạng thái -->
        <td style="text-align: center;">
          <a class="edit-btn" href="javascript:void(0);">
            <i class="bx bx-edit-alt me-1"></i>
          </a>
          <a class="delete-btn" href="javascript:void(0);">
            <i class="bx bx-trash me-1"></i>
          </a>
        </td>
      </tr>
    `)
      .join("");  // Tạo chuỗi HTML cho các dòng trong bảng

    // Gán HTML cho phần tử tbody
    document.querySelector("#tableData tbody").innerHTML = rows;

    // Khởi tạo lại DataTable với các tùy chọn phân trang
    if ($.fn.dataTable.isDataTable('#tableData')) {
      $('#tableData').DataTable().clear().destroy();  // Hủy bỏ phiên bản cũ trước khi khởi tạo lại
    }

    const table = $('#tableData').DataTable({
      "pageLength": 10,  // Hiển thị 2 bản ghi trên mỗi trang
      "lengthMenu": [2, 5, 10, 25],  // Các tùy chọn cho người dùng chọn số lượng bản ghi
      initComplete: function () {
        this.api()
          .columns()
          .every(function () {
            let column = this;

            // Kiểm tra xem có phải cột "Trạng thái" không
            if (column.index() === 3) {  // Cột "Trạng thái" có index là 3
              // Tạo phần tử select cho cột Trạng thái
              let select = document.createElement('select');
              select.add(new Option('')); // Thêm option trống
              column.footer().replaceChildren(select);

              // Thêm sự kiện thay đổi giá trị cho select
              select.addEventListener('change', function () {
                column
                  .search(select.value, {exact: true})
                  .draw();
              });

              // Thêm các giá trị duy nhất từ cột vào select
              column
                .data()
                .unique()
                .sort()
                .each(function (d, j) {
                  select.add(new Option(d));
                });
            } else {
              // Cột khác không làm gì, không cần thêm dropdown
              column.footer().innerHTML = '';  // Xóa footer nếu không có dropdown
            }
          });
      }
    });

    // Gắn lại sự kiện cho các nút sửa và xóa sau khi DataTable được khởi tạo lại
    $('#tableData').on('click', '.edit-btn', function() {
      const row = $(this).closest('tr');
      const id = row.data('id'); // Lấy ID của dòng
      editItem(id);
    });

    $('#tableData').on('click', '.delete-btn', function() {
      const row = $(this).closest('tr');
      const id = row.data('id'); // Lấy ID của dòng
      openDeleteModel(id);
    });

  } catch (error) {
    console.error("Error rendering table:", error);  // In lỗi nếu có
  }
};


const renderBooksList = async () => {
  try {
    const data = await axios.get(PATH_BOOK);
    let txt = `<option value="">-- Chọn sách --</option>`;
    data?.data?.forEach((element) => {
      txt += `<option value="${element.id}">${element?.title}</option>`;
    });

    const dataMem = await axios.get(PATH_MEMBER);
    let txtMem = `<option value="">-- Chọn thành viên --</option>`;
    dataMem?.data?.forEach((element) => {
      txtMem += `<option value="${element.id}">${element?.fullName}</option>`;
    });

    bookID.innerHTML = txt; // Cập nhật danh sách sách
    memID.innerHTML = txtMem; // Cập nhật danh sách thành viên
  } catch (error) {
    console.error("Error loading book and member data", error);
  }
};


const openModal = () => $(modalEditId).modal("show");


const closeModal = () => {
  $(modalEditId).modal("hide");  // Đóng modal chỉnh sửa/thêm
  $(modelConfirmId).modal("hide"); // Đóng modal xác nhận xóa (nếu cần)
  statusBook.disabled = true;  // Đặt lại trạng thái các input
  bookID.disabled = false;
  memID.disabled = false;
  clearEditForm();  // Xóa dữ liệu cũ trên form
};


const getFormData = () => ({
  ...(selectedItem || {}),
  borrowDate: borrowDate.value.trim(),
  returnDate: returnDate.value.trim(),
  status: statusBook.value.trim(),
  bookID: +bookID.value.trim(),
  memID: +memID.value.trim(),
});
const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const date = new Date(dateStr);  // Giữ nguyên ngày UTC
  // Đảm bảo hiển thị theo múi giờ Việt Nam mà không bị lệch
  return date.toLocaleDateString("vi-VN"); 
};

const setFormData = (data) => {
  console.log('Setting form data:', data); 

  // Cập nhật tên sách và tên người mượn (chỉ hiển thị, không sửa được)
  document.getElementById('bookTitle').value = data?.bookTitle || "";
  document.getElementById('memberFullName').value = data?.memberFullName || "";

  // Cập nhật ngày mượn
  if (data?.borrowDate) {
    const localBorrowDate = new Date(data.borrowDate);
    document.getElementById('eborrowDate').value = localBorrowDate.toLocaleDateString('en-CA');  // 'en-CA' = 'YYYY-MM-DD'
  } else {
    document.getElementById('eborrowDate').value = "";
  }

  // Cập nhật ngày trả
  if (data?.returnDate) {
    const localReturnDate = new Date(data.returnDate);
    document.getElementById('ereturnDate').value = localReturnDate.toLocaleDateString('en-CA');  // 'en-CA' = 'YYYY-MM-DD'
  } else {
    document.getElementById('ereturnDate').value = "";
  }

  // Cập nhật trạng thái
  document.getElementById('status').value = data?.status || "";

  // Cập nhật thông tin sách và thành viên (trong trường hợp bạn muốn lưu thông tin này nhưng không cho phép chỉnh sửa)
  document.getElementById('bookID').value = data?.bookID?.id || "";  
  document.getElementById('memID').value = data?.memberID?.id || "";  
};




// Đổi tên hàm xóa dữ liệu cũ của form khi mở modal chỉnh sửa
const clearEditForm = () => {
  selectedItem = null;
  borrowDate.value = "";  
  returnDate.value = "";  
  statusBook.value = "pending";
  bookID.value = "";
  memID.value = "";
};

// Đổi tên hàm xóa dữ liệu cũ khi mở modal thêm mới
const clearAddForm = () => {
  selectedItem = null;
  borrowDate.value = ""; 
  returnDate.value = ""; 
  statusBook.value = "pending";
  bookID.value = "";
  memID.value = "";
};

const handleFormSubmit = async (event) => {
  event.preventDefault();

  const borrowRequest = {
    bookID: document.getElementById('bookID').value,
    memID: document.getElementById('memID').value,
    borrowDate: document.getElementById('borrowDate').value,
    returnDate: document.getElementById('returnDate').value,
    status: document.getElementById('status').value,
  };

  console.log('Form Data:', borrowRequest); // Kiểm tra giá trị form dữ liệu

  if (!borrowRequest.bookID || !borrowRequest.memID || !borrowRequest.borrowDate) {
    alert("Vui lòng điền đầy đủ thông tin.");
    return;
  }

  const borrowDate = new Date(borrowRequest.borrowDate);
  const returnDate = new Date(borrowRequest.returnDate);

  if (returnDate <= borrowDate) {
    alert("Ngày trả phải muộn hơn ngày mượn ít nhất 1 ngày.");
    return;
  }

  const maxReturnDate = new Date(borrowDate);
  maxReturnDate.setMonth(maxReturnDate.getMonth() + 3);

  if (returnDate > maxReturnDate) {
    alert("Ngày trả không được vượt quá 3 tháng kể từ ngày mượn.");
    return;
  }

  try {
    const response = await axios.post(PATH, borrowRequest);
    console.log('Borrow request created:', response.data);
    
    // Đóng modal sau khi tạo thành công
    closeModala();

    // Tự động tải lại trang sau khi thêm mới thành công
    location.reload();  // Tải lại trang

    alert('Yêu cầu mượn sách đã được tạo thành công!');
  } catch (error) {
    console.error('Error:', error);
    alert('Đã xảy ra lỗi, vui lòng thử lại!');
  }
};


const closeModala = () => {
  $('#modelAdd').modal('hide'); // Đảm bảo sử dụng ID đúng của modal
  clearAddForm(); // Đặt lại form

};


const editItem = (id) => {
  // Tìm item dựa trên ID
  selectedItem = items.find((item) => item.id === id);

  if (selectedItem) {
    // Kiểm tra dữ liệu trong selectedItem
    console.log(selectedItem); // In ra để kiểm tra

    // Gọi setFormData để render dữ liệu cũ vào các input
    setFormData(selectedItem);

    // Mở modal chỉnh sửa
    openModal();

    // Tắt các trường không cần chỉnh sửa (bookID, memID)
    bookID.disabled = false;
    memID.disabled = false;
    statusBook.disabled = false;
  }
};



const handleEditFormSubmit = async (event) => {
  event.preventDefault();

  const updatedData = {
    id: selectedItem.id,
    bookID: document.getElementById('bookID').value,
    memID: document.getElementById('memID').value,
    borrowDate: document.getElementById('eborrowDate').value,
    returnDate: document.getElementById('ereturnDate').value,
    status: document.getElementById('status').value,
  };

  const borrowDate = new Date(updatedData.borrowDate);
  const returnDate = new Date(updatedData.returnDate);

  if (returnDate <= borrowDate) {
    alert("Ngày trả phải muộn hơn ngày mượn ít nhất 1 ngày.");
    return;
  }

  const maxReturnDate = new Date(borrowDate);
  maxReturnDate.setMonth(maxReturnDate.getMonth() + 3);

  if (returnDate > maxReturnDate) {
    alert("Ngày trả không được vượt quá 3 tháng kể từ ngày mượn.");
    return;
  }

  try {
    const response = await axios.put(`${PATH}/${updatedData.id}`, updatedData);
    location.reload();
    alert('Cập nhật thông tin mượn sách thành công!');
    renderTable();  // Reload bảng để hiển thị dữ liệu mới
    closeModal();
  } catch (error) {
    console.error("Error updating borrow request:", error);
    alert("Cập nhật thất bại, vui lòng thử lại.");
  }
};

const openDeleteModel = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    $(modelConfirmId).modal("show");
  }
};

const addItem = () => {
  clearAddForm();  // Gọi hàm clearAddForm khi mở modal thêm mới
  openModal();
};

const deleteItem = async () => {
  try {
    // Xóa item từ API
    await axios.delete(PATH + `/${selectedItem.id}`);

    // Hủy bỏ DataTable hiện tại
    if ($.fn.dataTable.isDataTable('#tableData')) {
      $('#tableData').DataTable().clear().destroy(); // Hủy DataTable cũ
    }

    // Cập nhật lại bảng sau khi xóa
    renderTable();  // Tái tạo lại bảng và khởi tạo lại DataTable

    alert("Yêu cầu mượn sách đã được xóa thành công!"); // Thông báo thành công
  } catch (error) {
    console.error("Error deleting item:", error);  // In lỗi nếu có
    alert("Đã xảy ra lỗi khi xóa, vui lòng thử lại.");
  } finally {
    closeModal();  // Đóng modal sau khi hoàn thành thao tác
  }
};