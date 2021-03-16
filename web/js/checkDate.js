function mainName() {
    var rentalDate = document.getElementById("dateRent").value;
    var returnDate = document.getElementById("dateReturn").value;
    if (returnDate > rentalDate) {
        document.getElementById("btnClickName").innerHTML = `
        <button type="submit" class="btn btn-primary">Search</button>
        `;
    } else {
        document.getElementById("errName").innerHTML =
                "Return Date must bigger than rental Date";
    }
}
function mainCategory() {
    var rentalDate = document.getElementById("dateRentt").value;
    var returnDate = document.getElementById("dateReturnn").value;
    if (returnDate > rentalDate) {
        document.getElementById("btnClickCategory").innerHTML = `
        <button type="submit" class="btn btn-primary">Search</button>
        `;
    } else {
        document.getElementById("errCategory").innerHTML =
                "Return Date must bigger than rental Date";
    }
}
