function hideHeading() {
  document.querySelector("h1").style.display = "none";
}

function showHeading() {
  document.querySelector("h1").style.display = "block";
}

function changeHeadingColor() {
  document.getElementById("demo-heading").style.color = "blue";
}

// function showOrHide() {
//   const heading = document.querySelector("h1");
//   if (heading.style.display === "none" || heading.style.display === "") {
//     heading.style.display = "block";
//   } else {
//     heading.style.display = "none";
//   }
// }

// document.getElementById("two-funct-btn").addEventListener("click", showOrHide);
function toggleHeading() {
  document.querySelector("h1").classList.toggle("hidden");
}

document
  .getElementById("two-funct-btn")
  .addEventListener("click", toggleHeading);
