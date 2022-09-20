fetch("http://localhost:8080/api/about").then(
    res=>{
        res.json().then(
            data=> {
                document.getElementById("userEmail").innerText = data.email;
                document.getElementById("roleUser").innerText = data.rolesName;
                let temp = `<tr>
                         <td>${data.id}</td>
                         <td>${data.firstName}</td>  
                         <td>${data.lastName}</td>  
                         <td>${data.age}</td>  
                         <td>${data.email}</td>  
                         <td>${data.rolesName}</td></tr>`
                document.getElementById("tableBody").innerHTML = temp;
            }
        )
    }
)