const tableData = document.querySelector("#tableData");
const tableDataDialog = document.querySelector("#tableDataDialog");
const title = document.querySelector("#title");
const author = document.querySelector("#author");
const publishYear = document.querySelector("#publishYear");
const quantity = document.querySelector("#quantity");
const cateID = document.querySelector("#cateID");
const modalEditId = "#modelEdit";
const modalViewId = "#modelView";
const modalAddId = "#modelAdd";
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
         <td>${item.publishYear}</td> <!-- Hiển thị năm xuất bản -->
        <td>${item.quantity}</td> <!-- Hiển thị số lượng -->
        <td>
          ${item.categoryDtos && item.categoryDtos.length > 0
            ? item.categoryDtos.map(category => category.name).join(', ')
            : 'No categories'}
        </td>
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
  $(modalAddId).modal("hide");
  clearForm();
  tableDataDialog.innerHTML = "";
};
const getFormDatae = () => {
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
const getFormData = () => {
  // Lấy danh sách các checkbox được chọn
  const selectedCategories = Array.from(
    document.querySelectorAll('#cateID input[type="checkbox"]:checked')
  ).map((checkbox) => parseInt(checkbox.value, 10)); // Chuyển giá trị sang số nguyên

  // Trả về dữ liệu với kiểm tra rõ ràng
  const data = {
    ...(selectedItem || {}), // Dữ liệu hiện tại nếu đang chỉnh sửa
    title: title.value.trim(), // Loại bỏ khoảng trắng
    author: author.value.trim(),
    publishYear: publishYear.value.trim() ? parseInt(publishYear.value.trim(), 10) : null, // Kiểm tra giá trị số
    quantity: quantity.value.trim() ? parseInt(quantity.value.trim(), 10) : null,
    categoryIds: selectedCategories.length > 0 ? selectedCategories : null, // Trả về `null` nếu không có danh mục nào được chọn
  };

  // Kiểm tra dữ liệu đã thu thập (debug)
  console.log("Form Data:", data);

  return data;
};






const clearForm = () => {
  title.value = "";  // Reset trường Title
  author.value = "";  // Reset trường Author
  publishYear.value = "";  // Reset trường Publish Year
  quantity.value = "";  // Reset trường Quantity
  
  // Reset tất cả các checkbox trong cateID
  Array.from(document.querySelectorAll('#cateID input[type="checkbox"]')).forEach((checkbox) => {
    checkbox.checked = false;  // Đặt lại trạng thái checkbox thành chưa chọn
  });
  
  selectedItem = null;  // Đặt lại selectedItem
};


// Xử lý submit form thêm/sửa

// Đặt dữ liệu lên form khi chỉnh sửa
const setFormData = (data) => {
  title.value = data?.title || "";
  author.value = data?.author || "";
  publishYear.value = data?.publishYear || "";
  quantity.value = data?.quantity || "";

  // Xử lý thể loại (nếu có)
  
};


const editItem = (id) => {
  // Tìm sách theo ID
  selectedItem = items.find((item) => item.id === id);

  if (selectedItem) {
    // Gán giá trị vào form chỉnh sửa
    document.querySelector("#edit-title").value = selectedItem.title || "";
    document.querySelector("#edit-author").value = selectedItem.author || "";
    document.querySelector("#edit-publishYear").value = selectedItem.publishYear || "";
    document.querySelector("#edit-quantity").value = selectedItem.quantity || "";

    // Chuyển categoryDtos thành categoryIds trước khi mở modal
    selectedItem.categoryIds = selectedItem.categoryDtos ? selectedItem.categoryDtos.map(cate => cate.id) : [];

    // Gọi API và render danh sách thể loại vào modal
    renderCateLista().then(() => {
      // Đảm bảo checkbox được checked sau khi render xong
      const selectedCategories = selectedItem.categoryDtos || [];

      // Đánh dấu checkbox dựa trên selectedCategories
      Array.from(document.querySelectorAll('#cate input[type="checkbox"]')).forEach((checkbox) => {
        // Kiểm tra xem categoryDtos của sách có chứa thể loại tương ứng với checkbox không
        checkbox.checked = selectedCategories.some((cate) => cate.id === +checkbox.value);
      });

      // Mở modal chỉnh sửa
      $(modalEditId).modal("show");
    });
  } else {
    console.error(`Không tìm thấy sách với ID: ${id}`);
  }
};


// Render danh sách thể loại vào modal
const renderCateLista = async () => {
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
            <label class="form-check-label" for="cate-${cate.id}">${cate.name}</label>
          </div>
        `;
      });
    }
    
    // Đưa HTML vào phần tử có id là "cate"
    document.getElementById("cate").innerHTML = checkboxes;

    // Trả về Promise để biết khi nào render xong
    return Promise.resolve();
  } catch (error) {
    console.error("Error loading categories:", error);
    return Promise.reject(error);
  }
};

const handleFormSubmit = async (event) => {
  event.preventDefault();

  // Lấy dữ liệu từ form
  const formData = getFormData();

  // Kiểm tra điều kiện dữ liệu
  if (!formData.title || !formData.author || !formData.publishYear || !formData.quantity || !formData.categoryIds) {
    alert("Vui lòng điền đầy đủ thông tin và chọn ít nhất một thể loại!");
    return;
  }

  try {
    const response = await axios.post(PATH, formData);
    alert("Thêm mới sách thành công!");
    renderTable(); // Cập nhật bảng
  } catch (error) {
    console.error("Error submitting form:", error);
    alert("Có lỗi xảy ra. Vui lòng thử lại.");
  } finally {
    closeModal(); // Đóng modal
  }
};



const handleEditFormSubmit = async (event) => {
  event.preventDefault();

  // Cập nhật các thông tin từ form
  selectedItem.title = document.querySelector("#edit-title").value;
  selectedItem.author = document.querySelector("#edit-author").value;
  selectedItem.publishYear = document.querySelector("#edit-publishYear").value;
  selectedItem.quantity = document.querySelector("#edit-quantity").value;

  // Cập nhật categoryIds (sử dụng categoryDtos để tạo categoryIds)
  selectedItem.categoryIds = [];
  Array.from(document.querySelectorAll('#cate input[type="checkbox"]:checked')).forEach((checkbox) => {
    selectedItem.categoryIds.push(+checkbox.value);
  });

  try {
    // Gửi yêu cầu PUT đến API để cập nhật sách
    const response = await axios.put(`${PATH}/${selectedItem.id}`, selectedItem);

    if (response.status === 200) {
      alert("Cập nhật thông tin sách thành công!");
      renderTable();  // Reload bảng sau khi cập nhật
    } else {
      alert("Có lỗi xảy ra khi cập nhật sách.");
    }
  } catch (error) {
    console.error("Error updating book:", error);
    alert("Có lỗi xảy ra. Vui lòng thử lại.");
  } finally {
    // Đóng modal sau khi hoàn thành
    closeModal();
  }
};


const viewItem = async (bookId) => {
  try {
    const { data } = await axios.get(PATH_REVIEW + "/by-book", { params: { bookId } });
    const rows = Array.isArray(data) 
      ? data.map(
        (review) => `
      <tr>
        <td>${review.memberFullName}</td>  <!-- Sử dụng memberFullName thay vì member_id.fullName -->
        <td>
          <textarea rows="4" cols="40" disabled>${review.comment}</textarea>
        </td>
        <td>${review.rating}</td>
      </tr>
    `).join("")
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
