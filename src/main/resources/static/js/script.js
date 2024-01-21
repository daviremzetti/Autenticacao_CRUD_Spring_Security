async function logar(login, senha){
    const conexao = await fetch("http://localhost:8080/auth/login", {
        method: 'POST',
        headers:{
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({login, senha}),
    })
    const json = await conexao.json();
    const token = json.token;
    localStorage.setItem('token', token);
    const role = await verificarRole(login, senha);
    localStorage.setItem('role', role);
}

async function verificarRole(login, senha){
    const conexao = await fetch("http://localhost:8080/auth/buscarUsuario", {
        method: "POST",
        headers:{
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify({login, senha})
    })
    const json = await conexao.json();
    const role = json.role;
    return role;
}

document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector(".formulario");
    form.addEventListener('submit', async function (event) {
        event.preventDefault();
        var login = document.querySelector(".login").value;
        var senha = document.querySelector(".senha").value;
        await logar(login, senha);
        window.location.href = "http://localhost:8080/home";
    });
});