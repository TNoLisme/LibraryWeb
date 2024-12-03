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

const getFormData = () => ({
  // ...(selectedItem || {}),
  comment: comment.value.trim(),
  rating: +rating.value.trim(),
  borrowRequestId: selectedItem?.id,
  memberId: selectedItem?.memberID?.id,
  reviewDate: new Date().toISOString(),
});

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
    if (selectedItem) {
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
