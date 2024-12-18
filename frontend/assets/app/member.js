const tableData = document.querySelector("#tableData");
const typeNameInput = document.querySelector("#fullName");
const typeEmailInput = document.querySelector("#email");
const typePasswordInput = document.querySelector("#password");
const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/members";

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
});

const renderTable = async () => {
  try {
    const data = await axios.get(PATH);
    items = data?.data || [];
    const rows = data?.data
      .map(
        (item) => `
      <tr>
        <td>${item.fullName}</td>
        <td>${item.email}</td>
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

const openModal = () => $(modalEditId).modal("show");

const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  clearForm();
};

const getFormData = () => ({
  ...(selectedItem || {}),
  fullName: typeNameInput.value.trim(),
  email: typeEmailInput.value.trim(),
  password: typePasswordInput.value.trim(),
});

const setFormData = (data) => {
  typeNameInput.value = data?.fullName || "";
  email.value = data.email;
  password.value = data.password;
};

const clearForm = () => {
  typeNameInput.value = "";
  email.value = "";
  password.value = "";
  selectedItem = null;
};

const handleFormSubmit = async (event) => {
  event.preventDefault();
  try {
    const formData = getFormData();
    if (selectedItem) {
      await axios.put(PATH + `/${selectedItem.id}`, formData);
    } else {
      const response = await axios.post(PATH, formData);

      // Kiểm tra trạng thái 201 (Created)
      if (response.status === 201) {
        alert("Thêm mới thành công!");
      }
    }
  } catch (error) {
    // Kiểm tra lỗi 409 (Email đã tồn tại)
    if (error.response && error.response.status === 409) {
      alert("Email đã tồn tại");
    } else {
      alert(error.response?.data || "An unexpected error occurred.");
    }
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
