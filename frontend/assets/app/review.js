const tableData = document.querySelector("#tableData");
const reviewDate = document.querySelector("#reviewDate");
const comment = document.querySelector("#comment");
const rating = document.querySelector("#rating");
const memberID = document.querySelector("#memberID");
const bookID = document.querySelector("#bookID");
const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/reviews";
const PATH_BOOK = BASE_URL + "/api/books";
const PATH_MEMBER = BASE_URL + "/api/members";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
  renderOptionList();
});

const renderTable = async () => {
  try {
    const data = await axios.get(PATH);
    items = data?.data || [];
    const rows = data?.data
      .map(
        (item) => `
      <tr>
        <td>${item.bookID?.title}</td>
        <td>${item.memberID?.fullName}</td>
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

const renderOptionList = async () => {
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
    memberID.innerHTML = txtMem;
  } catch (error) {}
};

const openModal = () => $(modalEditId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");

  bookID.disabled = false;
  memberID.disabled = false;
  clearForm();
};

const getFormData = () => ({
  ...(selectedItem || {}),
  reviewDate: reviewDate.value.trim() + "T00:00:00Z",
  comment: comment.value.trim(),
  rating: rating.value.trim(),
  memberID: memberID.value.trim(),
  bookID: bookID.value.trim(),
});

const setFormData = (data) => {
  reviewDate.value = data?.reviewDate?.replace("T00:00:00Z", "") || "";
  comment.value = data?.comment || "";
  rating.value = data?.rating || "";
  memberID.value = data?.memberID?.id || "";
  bookID.value = data?.bookID?.id || "";
};

const clearForm = () => {
  reviewDate.value = "";
  comment.value = "";
  rating.value = "";
  memberID.value = "";
  bookID.value = "";
  selectedItem = null;
};

const handleFormSubmit = async (event) => {
  event.preventDefault();
  try {
    const formData = getFormData();
    if (selectedItem) {
      await axios.put(PATH + `/${selectedItem.id}`, formData);
    } else {
      await axios.post(PATH, formData);
    }
  } catch (error) {
    console.error("Error submitting form:", error);
  } finally {
    closeModal();
    renderTable();
  }
};

const editItem = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    setFormData(selectedItem);
    openModal();
    bookID.disabled = true;
    memberID.disabled = true;
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
