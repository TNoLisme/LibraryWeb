const tableData = document.querySelector("#tableData");
const borrowDate = document.querySelector("#borrowDate");
const returnDate = document.querySelector("#returnDate");
const statusBook = document.querySelector("#status");
const bookID = document.querySelector("#bookID");
const memID = document.querySelector("#memID");

const modalEditId = "#modelEdit";
const modelConfirmId = "#modelConfirm";

// API để lấy penalties của member
const user = JSON.parse(sessionStorage.getItem("current-user")); // Lấy thông tin người dùng từ session
const apiEndpoint = `${BASE_URL}/api/penalties/member/${user.id}`; // Truyền trực tiếp vào API này

let selectedItem = null;
let items = [];

window.addEventListener("DOMContentLoaded", () => {
  renderTable();
});

const renderTable = async () => {
  try {
    // Gọi API lấy danh sách penalties cho user hiện tại
    const response = await fetch(apiEndpoint);
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
      
      `;

      tableData.appendChild(row);
    });

  } catch (error) {
    console.error("Lỗi khi render bảng:", error);
    alert("Không thể tải dữ liệu, vui lòng thử lại sau.");
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
  selectedItem = items.find((item) => item.id === id);

  if (selectedItem) {
    document.querySelector("#eborrowDate").value = selectedItem.borrowDate || "";
    document.querySelector("#ereturnDate").value = selectedItem.returnDate || "";
    document.querySelector("#status").value = selectedItem.status || "pending";

    $(modalEditId).modal("show"); // Hiển thị modal chỉnh sửa
  }
};

// Hàm xóa penalty
const deleteItem = async (id) => {
  if (confirm("Bạn có chắc chắn muốn xóa bản ghi này?")) {
    try {
      const response = await fetch(`${apiEndpoint}/${id}`, { method: "DELETE" });

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
