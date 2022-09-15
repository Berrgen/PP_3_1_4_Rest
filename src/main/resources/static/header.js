fetch("http://localhost:8080/api/about").then(
    res=>{
        res.json().then(
            data=> {
                document.getElementById("userEmail").innerText = data.email;
                document.getElementById("roleUser").innerText = data.rolesName;
            }
        )
    }
)