window.addEventListener("DOMContentLoaded", () => {
  if (!sessionStorage.getItem("current-user")) {
    window.location.href = "login.html";
  }
});
