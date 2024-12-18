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
    const items = Array.isArray(data) ? data : [];

    const rows = items
      .map(
        (item) => `
      <tr>
        <td>${item.title}</td> <!-- Hiển thị tên sách -->
        <td>${item.author}</td> <!-- Hiển thị tác giả -->
        <td>${item.publishYear}</td> <!-- Hiển thị năm xuất bản -->
        <td>${item.quantity}</td> <!-- Hiển thị số lượng -->
        <td>
    ${item.categoryDtos && item.categoryDtos.length > 0
            ? item.categoryDtos.map(category => category.name).join(', ')
            : 'No categories'}
</td>

        <td style="text-align: center;">
          <!-- Edit Button -->
          <a onclick="editItem(${item.id})" href="javascript:void(0);">
            <i class="bx bx-edit-alt me-1"></i>
          </a>
          
          <!-- Delete Button -->
          <a onclick="openDeleteModel(${item.id})" href="javascript:void(0);">
            <i class="bx bx-trash me-1"></i>
          </a>
          
          <!-- View Button -->
          <a onclick="viewItem(${item.id})" href="javascript:void(0);">
            <i class='bx bx-show'></i>
          </a>
        </td>
      </tr>
    `)
      .join(""); // Kết nối tất cả các hàng thành một chuỗi

    const tableData = document.getElementById("tableData");
    tableData.innerHTML = rows;

  } catch (error) {
    console.error("Error rendering table:", error);
  }
};


// Render danh sách thể loại trong dropdown
const renderCateList = async () => {
  try {
    // Gọi API để lấy dữ liệu thể loại sách
    const { data } = await axios.get(PATH_CATE);

    let checkboxes = ""; // Khởi tạo chuỗi chứa HTML cho các checkbox
    if (Array.isArray(data)) {  // Kiểm tra xem dữ liệu trả về có phải mảng không
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

    // Đưa HTML vào phần tử có id là "cateID"
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


const getFormData = () => {
  const selectedCategories = Array.from(document.querySelectorAll('#cateID input[type="checkbox"]:checked'))
    .map((checkbox) => parseInt(checkbox.value, 10));

  return {
    ...(selectedItem || {}),
    title: title.value.trim(),
    author: author.value.trim(),
    publishYear: +publishYear.value.trim(),
    quantity: +quantity.value.trim(),
    categoryIds: selectedCategories.length > 0 ? selectedCategories : null,
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

  // Lấy dữ liệu từ form
  const formData = getFormData();
  // Kiểm tra nếu các trường bắt buộc chưa được điền
  if (!formData.title || !formData.author) {
    alert("Vui lòng điền đầy đủ thông tin!");
    return;
  }

  try {
    if (selectedItem) {
      // Nếu có item được chọn (đang chỉnh sửa), thực hiện PUT
      const response = await axios.put(`${PATH}/${selectedItem.id}`, formData);
      alert("Cập nhật thông tin sách thành công!");
    } else {
      // Nếu không có item được chọn (thêm mới), thực hiện POST
      const response = await axios.post(PATH, formData);

      alert("Thêm mới sách thành công!");
    }
  } catch (error) {
    console.error("Error submitting form:", error);
    alert("Có lỗi xảy ra. Vui lòng thử lại.");
  } finally {
    closeModal();  // Đóng modal sau khi gửi dữ liệu
    renderTable();  // Reload lại bảng để hiển thị dữ liệu mới
  }
};
// Xử lý chỉnh sửa sách
const handleEditFormSubmit = async (event) => {
  event.preventDefault(); // Ngăn không cho form reload trang

  // Lấy dữ liệu từ form
  const formData = {
    title: document.querySelector("#edit-title").value.trim(),
    author: document.querySelector("#edit-author").value.trim(),
    publishYear: +document.querySelector("#edit-publishYear").value.trim(),
    quantity: +document.querySelector("#edit-quantity").value.trim(),
  };

  // Kiểm tra dữ liệu
  if (!formData.title || !formData.author) {
    alert("Vui lòng điền đầy đủ thông tin!");
    return;
  }

  try {
    // Gửi yêu cầu cập nhật dữ liệu
    await axios.put(`${PATH}/${selectedItem.id}`, formData);
    alert("Cập nhật thông tin sách thành công!");
    renderTable(); // Tải lại danh sách sách
  } catch (error) {
    console.error("Error editing book:", error);
    alert("Có lỗi xảy ra. Vui lòng thử lại.");
  } finally {
    closeModal(); // Đóng modal chỉnh sửa
  }
};



const getEditFormData = () => ({
  ...selectedItem,
  title: document.querySelector("#edit-title").value.trim(),
  author: document.querySelector("#edit-author").value.trim(),
  // Các trường khác
});
const editItem = (id) => {
  // Tìm sách theo ID
  selectedItem = items.find((item) => item.id === id);

  if (selectedItem) {
    // Gán giá trị vào form chỉnh sửa
    document.querySelector("#edit-title").value = selectedItem.title || "";
    document.querySelector("#edit-author").value = selectedItem.author || "";
    document.querySelector("#edit-publishYear").value = selectedItem.publishYear || "";
    document.querySelector("#edit-quantity").value = selectedItem.quantity || "";

    // Đặt trạng thái cho các checkbox của category
    Array.from(document.querySelectorAll('#cateID input[type="checkbox"]')).forEach((checkbox) => {
      checkbox.checked = selectedItem?.categories?.some((cate) => cate.id === +checkbox.value) || false;
    });

    // Mở modal chỉnh sửa
    $(modalEditId).modal("show");
  } else {
    console.error(`Không tìm thấy sách với ID: ${id}`);
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
