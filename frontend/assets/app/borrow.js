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

const formatDate = (dateStr) => {
  if (!dateStr) return "";
  const date = new Date(dateStr);
  return date.toLocaleDateString("vi-VN"); // Định dạng ngày/tháng/năm
};

const renderTable = async () => {
  try {
    const data = await axios.get(PATH);
    items = data?.data || [];
    const rows = data?.data
      .map(
        (item) => `
      <tr>
        <td>${item.bookID?.title}</td>
        <td>${formatDate(item.borrowDate)}</td> <!-- Định dạng ngày tại đây -->
        <td style="text-align: center;">
          <a onclick="editItem(${item.id})" href="javascript:void(0);">
            <i class="bx bx-edit-alt me-1"></i>
          </a>
          <a onclick="openDeleteModel(${item.id})" href="javascript:void(0);">
            <i class="bx bx-trash me-1"></i>
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

const renderBooksList = async () => {
  try {
    const data = await axios.get(PATH_BOOK);
    let txt = `<option value="">-- Chọn sách --</option>`;
    data?.data?.forEach((element) => {
      txt += `<option value=${element.id}>${element?.title}</option>`;
    });
    const dataMem = await axios.get(PATH_MEMBER);
    let txtMem = `<option value="">-- Chọn thành viên --</option>`;
    dataMem?.data?.forEach((element) => {
      txtMem += `<option value=${element.id}>${element?.fullName}</option>`;
    });
    bookID.innerHTML = txt;
    memID.innerHTML = txtMem;
  } catch (error) {
    console.error("Error loading book and member data", error);
  }
};

const openModal = () => $(modalEditId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  statusBook.disabled = true;
  bookID.disabled = false;
  memID.disabled = false;
  clearForm();
};

const getFormData = () => ({
  ...(selectedItem || {}),
  borrowDate: borrowDate.value.trim(),
  returnDate: returnDate.value.trim(),
  status: statusBook.value.trim(),
  bookID: +bookID.value.trim(),
  memID: +memID.value.trim(),
});

const setFormData = (data) => {
  borrowDate.value = data?.borrowDate || "";
  returnDate.value = data?.returnDate || "";
  statusBook.value = data?.status || "";
  bookID.value = data?.bookID?.id || "";
  memID.value = data?.memberID?.id || "";
};

const clearForm = () => {
  selectedItem = null;
  borrowDate.value = null;
  returnDate.value = null;
  statusBook.value = "PENDING";
  bookID.value = null;
  memID.value = null;
};

const handleFormSubmit = async (event) => {
  event.preventDefault();

  const borrowRequest = {
    bookID: document.getElementById('bookID').value,
    memID: document.getElementById('memID').value,
    borrowDate: document.getElementById('borrowDate').value,
    returnDate: document.getElementById('returnDate').value,
    status: 'PENDING'
  };

  if (!borrowRequest.bookID || !borrowRequest.memID || !borrowRequest.borrowDate) {
    alert("Vui lòng điền đầy đủ thông tin.");
    return;
  }

  try {
    const response = await axios.post(PATH, borrowRequest);
    console.log('Borrow request created:', response.data);
    alert('Yêu cầu mượn sách đã được tạo thành công!');
    renderTable();  // Reload table after creation
    closeModal();
  } catch (error) {
    console.error('Error:', error);
    alert('Đã xảy ra lỗi, vui lòng thử lại!');
  }
};

const editItem = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    setFormData(selectedItem);
    openModal();
    statusBook.disabled = false;
    bookID.disabled = true;
    memID.disabled = true;
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