window.addEventListener("DOMContentLoaded", () => {
  if (!sessionStorage.getItem("current-user")) {
    window.location.href = "login.html";
  }
  if (JSON.parse(sessionStorage.getItem("current-user")).typeId === "member") {
    document.querySelector("#adminSidebar").style.display = "none";
    document.querySelector("#memberSidebar").style.display = "block";
  } else {
    document.querySelector("#adminSidebar").style.display = "block";
    document.querySelector("#memberSidebar").style.display = "none";
  }
});
