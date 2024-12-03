const tableData = document.querySelector("#tableData");
const tableDataDialog = document.querySelector("#tableDataDialog");
const title = document.querySelector("#title");
const author = document.querySelector("#author");
const publishYear = document.querySelector("#publishYear");
const quantity = document.querySelector("#quantity");
const cateID = document.querySelector("#cateID");
const modalEditId = "#modelEdit";
const modalViewId = "#modelView";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/books";
const PATH_CATE = BASE_URL + "/api/categories";
const PATH_REVIEW = BASE_URL + "/api/reviews";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
  renderCateList();
});

const renderTable = async () => {
  try {
    const data = await axios.get(PATH);
    items = data?.data || [];
    const rows = data?.data
      .map(
        (item) => `
      <tr>
        <td>${item.title}</td>
        <td>${item.author}</td>
        <td style="text-align: center;">
          <a onclick="editItem(${item.id})" href="javascript:void(0);">
            <i class="bx bx-edit-alt me-1"></i>
          </a>
          <a onclick="openDeleteModel(${item.id})" href="javascript:void(0);">
            <i class="bx bx-trash me-1"></i>
          </a>
          <a onclick="viewItem(${item.id})" href="javascript:void(0);">
            <i class='bx bx-show'></i>
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

const renderCateList = async () => {
  try {
    const data = await axios.get(PATH_CATE);
    let txt = `<option value="">-- Chọn thể loại sách --</option>`;
    data?.data?.forEach((element) => {
      txt += `<option value=${element.id}>${element?.name}</option>`;
    });
    cateID.innerHTML = txt;
  } catch (error) {}
};

const openModal = () => $(modalEditId).modal("show");
const openModalView = () => $(modalViewId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  $(modalViewId).modal("hide");
  clearForm();

  tableDataDialog.innerHTML = "";
};

const getFormData = () => ({
  ...(selectedItem || {}),
  title: title.value.trim(),
  author: author.value.trim(),
  publishYear: +publishYear.value.trim(),
  quantity: +quantity.value.trim(),
  cateID: +cateID.value.trim(),
});

const setFormData = (data) => {
  title.value = data?.title || "";
  author.value = data?.author || "";
  publishYear.value = data?.publishYear || "";
  quantity.value = data?.quantity || "";
  cateID.value = data?.cateID?.id || "";
};

const clearForm = () => {
  title.value = "";
  title.value = "";
  author.value = "";
  publishYear.value = "";
  quantity.value = "";
  cateID.value = "";
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
  }
};
const viewItem = async (bookId) => {
  try {
    let config = {
      params: { bookId },
    };
    const data = await axios.get(PATH_REVIEW + "/by-book", config);
    const rows = data?.data
      .map(
        (item) => `
    <tr>
      <td>${item.memberID?.fullName}</td>
      <td>
        <textarea
          rows="4"
          cols="40"
          disabled
        >${item.comment}</textarea>
      </td>
      <td>${item.rating}</td>
    </tr>
  `
      )
      .join("");
    tableDataDialog.innerHTML = rows;

    openModalView();
  } catch (error) {}
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
