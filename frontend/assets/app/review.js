const tableData = document.querySelector("#tableData");
const reviewDate = document.querySelector("#reviewDate");
const comment = document.querySelector("#comment");
const rating = document.querySelector("#rating");
const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/reviews";
const PATH_BOOK = BASE_URL + "/api/books";
const PATH_MEMBER = BASE_URL + "/api/members";
const PATH_BORROW = BASE_URL + "/api/borrowrequests";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
});

const renderTable = async () => {
  try {
    const user = JSON.parse(sessionStorage.getItem("current-user"));
    const config = {
      params: { memberId: user?.id },
    };
    const data = await axios.get(PATH_BORROW + `/members`, config);
    items = data?.data || [];
    console.log(items);
    
    const rows = items
      .map(
        (item) => `
      <tr>
        <td>${item.bookTitle}</td>  <!-- Lấy bookTitle từ DTO -->
        <td>${item.memberFullName}</td> <!-- Lấy bookAuthor từ DTO -->
        <td style="text-align: center;">
          <a onclick="editItem(${item.id})" href="javascript:void(0);">
            <i class="bx bx-edit-alt me-1"></i>
          </a>
        </td>
      </tr>
    `
      )
      .join("");
    tableData.innerHTML = rows;
  } catch (error) {
    console.error("Error rendering table:", error);
  }
};


const openModal = () => $(modalEditId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  clearForm();
};

const getFormData = () => {
  const user = JSON.parse(sessionStorage.getItem("current-user"));
  const memberId = user?.id;

  if (!memberId) {
    alert("Member ID không hợp lệ. Vui lòng đăng nhập lại.");
    return;  // Nếu memberId không hợp lệ, ngừng thực hiện
  }

  const formData = {
    comment: comment.value.trim(),
    rating: +rating.value.trim(),
    borrowRequestId: selectedItem?.id,  // Lấy borrowRequestId từ selectedItem
    memberId: memberId,  // Lấy memberId từ sessionStorage
    reviewDate: new Date().toISOString(),  // Thời gian đánh giá hiện tại
  };

  console.log("Form Data:", formData);  // Kiểm tra dữ liệu gửi lên server
  return formData;
};



const setFormData = (data) => {
  comment.value = data?.comment || "";
  rating.value = data?.rating || "";
};

const clearForm = () => {
  comment.value = "";
  rating.value = "";
  selectedItem = null;
};

const handleFormSubmit = async (event) => {
  event.preventDefault();

  try {
    const formData = getFormData();

    // Kiểm tra xem borrowRequestId và memberId có hợp lệ không
    if (!formData.borrowRequestId || !formData.memberId) {
      alert("Thông tin không hợp lệ. Vui lòng thử lại!");
      return;
    }

    const response = await axios.post(PATH, formData);  // Gửi dữ liệu đến API
    console.log(response.data);  // Kiểm tra phản hồi từ server

    alert('Đánh giá đã được lưu thành công!');
    renderTable();  // Cập nhật lại bảng sau khi gửi thành công
  } catch (error) {
    console.error("Error submitting form:", error);
    alert('Có lỗi xảy ra khi lưu đánh giá!');
  } finally {
    closeModal();  // Đóng modal sau khi hoàn thành
  }
};




const editItem = (id) => {
  // Tìm record trong danh sách items theo ID
  selectedItem = items.find((item) => item.id === id);

  if (selectedItem) {
    // Khi đã chọn item, điền dữ liệu vào form
    setFormData(selectedItem);
    openModal();  // Hiển thị modal chỉnh sửa
  }
};


const openDeleteModel = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    $(modelConfirmId).modal("show");
  }
};

const addItem = () => {
  clearForm();
  openModal();
};

const deleteItem = async () => {
  try {
    await axios.delete(PATH + `/${selectedItem.id}`);
    renderTable();
  } catch (error) {
    console.error("Error deleting item:", error);
  } finally {
    closeModal();
  }
};
