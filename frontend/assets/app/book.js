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

// Load dữ liệu khi DOM đã sẵn sàng
window.addEventListener("DOMContentLoaded", () => {
  renderTable();
  renderCateList();
});

// Render bảng sách
const renderTable = async () => {
  try {
    const { data } = await axios.get(PATH);
    items = Array.isArray(data) ? data : []; // Kiểm tra xem dữ liệu có phải mảng không
    const rows = items
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

// Render danh sách thể loại trong dropdown
const renderCateList = async () => {
  try {
    const { data } = await axios.get(PATH_CATE); 
    let checkboxes = ""; 
    if (Array.isArray(data)) { 
      data.forEach((cate) => {
        checkboxes += `
          <div class="form-check">
            <input 
              class="form-check-input" 
              type="checkbox" 
              id="cate-${cate.id}" 
              value="${cate.id}" 
            />
            <label class="form-check-label" for="cate-${cate.id}">
              ${cate.name}
            </label>
          </div>
        `;
      });
    }
    document.getElementById("cateID").innerHTML = checkboxes;
  } catch (error) {
    console.error("Error loading categories:", error); 
  }
};


// Hiển thị modal thêm/sửa
const openModal = () => $(modalEditId).modal("show");
const openModalView = () => $(modalViewId).modal("show");

// Đóng modal và reset form
const closeModal = () => {
  $(modalEditId).modal("hide");
  $(modelConfirmId).modal("hide");
  $(modalViewId).modal("hide");
  clearForm();
  tableDataDialog.innerHTML = "";
};

// Lấy dữ liệu từ form
const getFormData = () => {
  const selectedCategories = Array.from(cateID.selectedOptions).map((option) =>
    parseInt(option.value, 10)
  );
  return {
    ...(selectedItem || {}),
    title: title.value.trim(),
    author: author.value.trim(),
    publishYear: +publishYear.value.trim(),
    quantity: +quantity.value.trim(),
    categoryIds: selectedCategories.length > 0 ? selectedCategories : null, // Kiểm tra để đảm bảo không gửi mảng rỗng
  };
};

// Đặt dữ liệu lên form khi chỉnh sửa
const setFormData = (data) => {
  title.value = data?.title || "";
  author.value = data?.author || "";
  publishYear.value = data?.publishYear || "";
  quantity.value = data?.quantity || "";

  // Đặt thể loại
  Array.from(cateID.options).forEach((option) => {
    option.selected = data?.categories?.some((cate) => cate.id === +option.value) || false;
  });
};

// Xóa dữ liệu trên form
const clearForm = () => {
  title.value = "";
  author.value = "";
  publishYear.value = "";
  quantity.value = "";
  Array.from(cateID.options).forEach((option) => {
    option.selected = false;
  });
  selectedItem = null;
};

// Xử lý submit form thêm/sửa
const handleFormSubmit = async (event) => {
  event.preventDefault();
  try {
    const formData = getFormData();
    if (!formData.title || !formData.author) {
      alert("Vui lòng điền đầy đủ thông tin!");
      return;
    }
    if (selectedItem) {
      await axios.put(PATH + `/${selectedItem.id}`, formData);
    } else {
      await axios.post(PATH, formData);
    }
    alert("Lưu thành công!");
  } catch (error) {
    console.error("Error submitting form:", error);
    alert("Có lỗi xảy ra. Vui lòng thử lại.");
  } finally {
    closeModal();
    renderTable();
  }
};

// Chỉnh sửa sách
const editItem = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    setFormData(selectedItem);
    openModal();
  }
};

// Xem đánh giá sách
const viewItem = async (bookId) => {
  try {
    const { data } = await axios.get(PATH_REVIEW + "/by-book", { params: { bookId } });
    const rows = Array.isArray(data) 
      ? data.map(
        (review) => `
      <tr>
        <td>${review.memberID?.fullName}</td>
        <td>
          <textarea rows="4" cols="40" disabled>${review.comment}</textarea>
        </td>
        <td>${review.rating}</td>
      </tr>
    `
      ).join("")
      : "Không có đánh giá nào.";
    tableDataDialog.innerHTML = rows;
    openModalView();
  } catch (error) {
    console.error("Error viewing reviews:", error);
  }
};

// Mở modal xóa sách
const openDeleteModel = (id) => {
  selectedItem = items.find((item) => item.id === id);
  if (selectedItem) {
    $(modelConfirmId).modal("show");
  }
};

// Thêm sách mới
const addItem = () => {
  clearForm();
  openModal();
};

// Xóa sách
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
