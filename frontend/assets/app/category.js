const tableData = document.querySelector("#tableData");
const typeNameInput = document.querySelector("#name");
const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/categories";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
});

const renderTable = async () => {
  try {
    const response = await axios.get(PATH);
    items = response?.data || [];
    const rows = items
      .map(
        (item) => `
        <tr>
          <td>${item.name}</td>
          <td style="text-align: center;">
            <a onclick="editItem(${item.id})" href="javascript:void(0);">
              <i class="bx bx-edit-alt me-1"></i>
            </a>
            <a onclick="openDeleteModel(${item.id})" href="javascript:void(0);">
              <i class="bx bx-trash me-1"></i>
            </a>
          </td>
        </tr>`
      )
      .join("");
    tableData.innerHTML = rows;
  } catch (error) {
    console.error("Error rendering table:", error.message);
    if (error.response) {
      console.error("Response data:", error.response.data);
      console.error("Response status:", error.response.status);
      console.error("Response headers:", error.response.headers);
    }
  }
};


const openModal = () => $(modalEditId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  clearForm();
};

const getFormData = () => ({
  ...(selectedItem || {}),
  name: typeNameInput.value.trim(),
});

const setFormData = (data) => {
  typeNameInput.value = data?.name || "";
};

const clearForm = () => {
  typeNameInput.value = "";
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
    // Gửi yêu cầu xóa thể loại
    const response = await axios.delete(PATH + `/${selectedItem.id}`);

    if (response.status === 204) {
      renderTable();  // Reload lại bảng sau khi xóa thành công
      alert("Xóa thể loại thành công!");
    } else {
      alert(response.data);  // Hiển thị thông báo lỗi nếu không thể xóa
    }
  } catch (error) {
    console.error("Error deleting item:", error);
    alert("Chỉ có thể xóa khi không còn sách tham chiếu tới");
  } finally {
    closeModal();
  }
};

