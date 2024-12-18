const tableData = document.querySelector("#tableData");
const borrowDate = document.querySelector("#borrowDate");
const returnDate = document.querySelector("#returnDate");
const statusBook = document.querySelector("#status");
const bookID = document.querySelector("#bookID");
const memID = document.querySelector("#memID");

const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";
const PATH = BASE_URL + "/api/penalties"; 
let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
});

const renderTable = async () => {
  try {
    // Gọi API lấy danh sách penalties
    const response = await fetch(PATH);
    if (!response.ok) {
      throw new Error("Không thể tải dữ liệu từ server.");
    }

    items = await response.json(); // Lưu danh sách penalties vào biến items

    // Xóa nội dung cũ trong bảng
    tableData.innerHTML = "";

    // Duyệt qua danh sách và render từng dòng
    items.forEach((item) => {
        const row = document.createElement("tr");
      
        row.innerHTML = `
          <td>${item.member.fullName || "N/A"}</td>
          <td>${item.penaltyDate || "N/A"}</td>
          <td>${item.fine.fineAmount || "0"}</td>
           <td>${item.fine.fineReason || "Không có lý do"}</td>
          <td>${formatStatus(item.paidStatus)}</td>
          <td>
            <a href="javascript:void(0);" onclick="openEditModal(${item.penaltyRecordID})">
        <i class="bx bx-edit-alt me-1"></i>
          </td>
        `;
      
        tableData.appendChild(row);
      });
      
  } catch (error) {
    console.error("Lỗi khi render bảng:", error);
    alert("Không thể tải dữ liệu, vui lòng thử lại sau.");
  }
};
const handleEditFormSubmit = async (event) => {
  event.preventDefault();

  if (!selectedItem) {
    alert("Không tìm thấy bản ghi.");
    return;
  }

  const newStatus = document.querySelector("#editPaidStatus").value;

  try {
    const response = await fetch(`${PATH}/${selectedItem.penaltyRecordID}/paid-status`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ paidStatus: newStatus }),
    });

    if (response.ok) {
      alert("Cập nhật trạng thái thành công!");
      closeEditModal();
      renderTable(); 
    } else {
      throw new Error("Cập nhật thất bại.");
    }
  } catch (error) {
    console.error("Lỗi khi cập nhật trạng thái:", error);
    alert("Không thể cập nhật trạng thái, vui lòng thử lại sau.");
  }
};

// Hàm format trạng thái
const formatStatus = (status) => {
    const statusMapping = {
      paid: "Đã trả",
      unpaid: "Chưa trả",
    };
    return statusMapping[status] || "Không xác định";
  };
  

// Hàm mở modal chỉnh sửa
const openEditModal = (id) => {
  selectedItem = items.find((item) => item.penaltyRecordID === id);

  if (selectedItem) {
    document.querySelector("#editPaidStatus").value = selectedItem.paidStatus;
    $(modalEditId).modal("show"); // Hiển thị modal chỉnh sửa
  }
};

const closeEditModal = () => {
  $(modalEditId).modal("hide");
};


// Hàm xóa penalty
const deleteItem = async (id) => {
  if (confirm("Bạn có chắc chắn muốn xóa bản ghi này?")) {
    try {
      const response = await fetch(`${PATH}/${id}`, { method: "DELETE" });

      if (response.ok) {
        alert("Xóa thành công!");
        renderTable(); // Reload danh sách sau khi xóa
      } else {
        throw new Error("Xóa thất bại.");
      }
    } catch (error) {
      console.error("Lỗi khi xóa bản ghi:", error);
      alert("Không thể xóa bản ghi, vui lòng thử lại sau.");
    }
  }
};
