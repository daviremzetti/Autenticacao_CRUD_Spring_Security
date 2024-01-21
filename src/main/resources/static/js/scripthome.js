document.querySelector(".formulario-usuario").style.display = "none";
document.querySelector(".tabela-usuario").style.display = "none";
document.querySelector(".formulario-produto").style.display = "none";
document.querySelector(".tabela-produto").style.display = "none";
const corpo_tabela_usuario = document.querySelector(".tabela-usuario-corpo");
const corpo_tabela_produto = document.querySelector(".tabela-produto-corpo");

async function logout(){
    const conexao = await fetch("http://localhost:8080/auth/logout", {
        method:"POST",
        headers:{
            'Content-Type': 'application/json; charset=UTF-8'
        }
    })
    if(conexao.ok){
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        window.location.href = "http://localhost:8080/autenticacao";
    }
}

function desabilitarBotoes(){
    const role = localStorage.getItem("role");
    if(role === "USER"){
        var botoes = document.querySelectorAll(".btn-adm");
        botoes.forEach(function(botao){
            botao.disabled = true;
        })
    }
}

function display_formulario_usuario(){
    var formulario_usuario = document.querySelector(".formulario-usuario");
    var tabela_usuario = document.querySelector(".tabela-usuario");
    var formulario_produto = document.querySelector(".formulario-produto");
    var tabela_produto = document.querySelector(".tabela-produto");
    
    if(formulario_usuario.style.display === "none"){
        formulario_usuario.style.display = "block";
        tabela_usuario.style.display = "none";
        formulario_produto.style.display = "none";
        tabela_produto.style.display = "none";
    }else{
        formulario_usuario.style.display = "none";
    }
}

function display_lista_usuario(){
    var formulario_usuario = document.querySelector(".formulario-usuario");
    var tabela_usuario = document.querySelector(".tabela-usuario");
    var formulario_produto = document.querySelector(".formulario-produto");
    var tabela_produto = document.querySelector(".tabela-produto");
    if(tabela_usuario.style.display === "none"){
        formulario_usuario.style.display = "none";
        tabela_usuario.style.display = "block";
        formulario_produto.style.display = "none";
        tabela_produto.style.display = "none";
    }else{
        tabela_usuario.style.display = "none";
    }
}

function display_formulario_produto(){
    var formulario_usuario = document.querySelector(".formulario-usuario");
    var tabela_usuario = document.querySelector(".tabela-usuario");
    var formulario_produto = document.querySelector(".formulario-produto");
    var tabela_produto = document.querySelector(".tabela-produto");
    if(formulario_produto.style.display === "none"){
        formulario_usuario.style.display = "none";
        tabela_usuario.style.display = "none";
        formulario_produto.style.display = "block";
        tabela_produto.style.display = "none";
    }else{
        formulario_produto.style.display = "none";
    }
}

function display_tabela_produto(){
    var formulario_usuario = document.querySelector(".formulario-usuario");
    var tabela_usuario = document.querySelector(".tabela-usuario");
    var formulario_produto = document.querySelector(".formulario-produto");
    var tabela_produto = document.querySelector(".tabela-produto");
    if(tabela_produto.style.display === "none"){
        formulario_usuario.style.display = "none";
        tabela_usuario.style.display = "none";
        formulario_produto.style.display = "none";
        tabela_produto.style.display = "block";
    }else{
        tabela_produto.style.display = "none";
    }
}

async function cadastrar_usuario(){
    const nome = document.querySelector(".nome").value;
    const login = document.querySelector(".login").value;
    const role = document.querySelector(".role").value;
    const senha = document.querySelector(".senha").value;
    const token = localStorage.getItem('token');
    
    const conexao = await fetch("http://localhost:8080/auth/registrar", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
        },
        body: JSON.stringify(
            {
                'nome': nome,
                'login': login,
                'role': role,
                'senha': senha
            }
        )
    })
    if(conexao.ok){
        alert("Usuário cadastrado com sucesso!")
        document.querySelector(".nome").value = "";
        document.querySelector(".login").value = "";
        document.querySelector(".role").value = "";
        document.querySelector(".senha").value = "";
    }
}

async function buscar_usuarios(){
    const token = localStorage.getItem('token');
    const conexao = await fetch("http://localhost:8080/auth/listar", {
        method: "GET",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
        }
    })
    const json = await conexao.json();
    return json;
}

async function listar_usuarios(){
    const dadosApi = await buscar_usuarios();
    corpo_tabela_usuario.innerHTML = `<tbody class="tabela-usuario-corpo">
  </tbody>`;
    dadosApi.forEach(usuario => criarLinhaUsuario( usuario.nome, usuario.login, usuario.role, usuario.senha))

}

async function cadastrar_produto(){
    const token = localStorage.getItem('token');
    const nome = document.querySelector(".nome_produto").value;
    const valor = document.querySelector(".valor_produto").value;
    const conexao = await fetch("http://localhost:8080/produto/cadastrar", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
        },
        body: JSON.stringify({
            'nome': nome,
            'valor': valor
        })
    });
    if(conexao.ok){
        alert('Produto cadastrado com sucesso!');
        document.querySelector(".nome_produto").value = "";
        document.querySelector(".valor_produto").value = "";
    }
}

async function buscar_produtos(){
    const token = localStorage.getItem('token');
    const conexao = await fetch("http://localhost:8080/produto/listar", {
        method: "GET",
        headers:{
            'Content-Type': 'application/json; charset=UTF-8',
            'Authorization': token
        }
    })
    const json = await conexao.json();
    return json;
}

async function listar_produtos(){
    const dadosApi = await buscar_produtos();
    corpo_tabela_produto.innerHTML = `<tbody class="tabela-produto-corpo"></tbody>`;
    dadosApi.forEach(produto => criarLinhaProduto(produto.nome, produto.valor));
}

function criarLinhaUsuario(nome, login, role, senha){
    const linha = document.createElement("tr");
    linha.innerHTML = `<tr>
    <td>${nome}</td>
    <td>${login}</td>
    <td>${role}</td>
    <td>${senha}</td>
  </tr>`
  corpo_tabela_usuario.appendChild(linha);
}

function criarLinhaProduto(nome, valor){
    const linha = document.createElement("tr");
    linha.innerHTML = `<tr>
    <td>${nome}</td>
    <td>${valor}</td>
    </tr>`
    corpo_tabela_produto.appendChild(linha);
}

document.addEventListener('DOMContentLoaded', function () {
    const botao_sair = document.querySelector(".btn-sair");
    botao_sair.addEventListener('click', async function (event) {
        event.preventDefault();
        await logout();
    });

    const botao_cad_usuario = document.querySelector(".cad_usuario");
    botao_cad_usuario.addEventListener('click', function (event) {
        event.preventDefault();
        display_formulario_usuario();
    })

    const botao_listar_usuario = document.querySelector(".listar_usuario");
    botao_listar_usuario.addEventListener('click', function (event) {
        event.preventDefault();
        listar_usuarios();
        display_lista_usuario();
    })

    const botao_cad_produto = document.querySelector(".cad_produto");
    botao_cad_produto.addEventListener('click', function (event) {
        event.preventDefault();
        display_formulario_produto();
    })

    const botao_listar_produto = document.querySelector(".listar_produto");
    botao_listar_produto.addEventListener('click', function (event){
        event.preventDefault();
        listar_produtos();
        display_tabela_produto();
    })

    const formulario_usuario = document.querySelector(".formulario-usuario");
    formulario_usuario.addEventListener('submit', async function (event) {
        event.preventDefault();
        await cadastrar_usuario();
    })

    const formulario_produto = document.querySelector(".formulario-produto");
    formulario_produto.addEventListener('submit', async function (event) {
        event.preventDefault();
        await cadastrar_produto();
    })
});

desabilitarBotoes();