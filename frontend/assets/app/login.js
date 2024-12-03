const typeId = document.getElementById("typeId");
const email = document.getElementById("email");
const password = document.getElementById("password");
window.addEventListener("DOMContentLoaded", () => {
  sessionStorage.removeItem("current-user");
});

const handleFormLogin = async (event) => {
  event.preventDefault();

  try {
    const path =
      typeId.value === "admin"
        ? BASE_URL + "/api/admin/login"
        : BASE_URL + "/api/members/login";

    const config = {
      params: {
        email: email.value,
        password: password.value,
      },
    };

    const response = await axios.post(path, null, config);
    
    sessionStorage.setItem(
      "current-user",
      JSON.stringify({ ...response?.data, typeId: typeId?.value })
    );
    window.location.href = "index.html";
  } catch (error) {
    toastr.error('Tên tài khoản hoặc mật khẩu không chính xác!');
    console.error("Error during login:", error);
  }
};
