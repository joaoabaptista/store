import { fetchAllItems, fetchConstructions, getConstructionItems } from "../Service/sevices.js";

window.onload = init;

function init() {

  
  const container = document.createElement('div');
  container.classList.add('page');
  document.body.appendChild(container);

  const topBar = createTopBar();

  const sideBar = createSideBar();

  container.appendChild(topBar);
  container.appendChild(sideBar);
}

function createTopBar(){
  const bar = document.createElement('div');
  bar.classList.add('topBar');

  const stock_btn = document.createElement('button')
  stock_btn.addEventListener('click', () => {
    /**redirect to search items page */
    console.log("Ir para pagina de itens")
  });
  const stockIcon = addIcon('Resouces/items.png', 'stock_icon');

  stock_btn.appendChild(stockIcon);
  bar.appendChild(stock_btn);

  return bar;
}

function createSideBar(){
  const bar = document.createElement('div');
  bar.classList.add('sideBar');

  const resumosBtn = document.createElement('a');
  resumosBtn.textContent = "Resumos";
  resumosBtn.href = 'ola';
  resumosBtn.addEventListener('click', openModal)

  bar.appendChild(resumosBtn);
  return bar;
}

function createModal() {
  const modalOverlay = document.createElement('div');
  modalOverlay.classList.add('modal-overlay');

  const modal = document.createElement('div');
  modal.classList.add('modal');

  const title = document.createElement('div');
  title.textContent = 'Digite o nº de Obra';

  const refInput = document.createElement('input');
  refInput.placeholder = 'Número da obra';

  const checkButton = document.createElement('button');
  checkButton.textContent = 'Procurar';
  checkButton.classList.add('check-button');
  checkButton.addEventListener('click', async () => {
    const constID = refInput.value.trim();
    if (!constID) {
      alert('Digite o número da obra!');
      return;
    }

    try {
      const items = await getConstructionItems(constID);
      console.log('Itens encontrados:', items);
      // Aqui você pode chamar uma função para exibir os itens
      modalOverlay.remove();
    } catch (error) {
      console.error('Erro ao buscar itens:', error);
      alert('Erro ao buscar os itens da obra.');
    }
  });

  const closeButton = document.createElement('button');
  closeButton.textContent = 'Fechar';
  closeButton.classList.add('close-button');
  closeButton.addEventListener('click', () => {
    modalOverlay.remove();
  });

  modal.appendChild(title);
  modal.appendChild(refInput);
  modal.appendChild(checkButton);
  modal.appendChild(closeButton);
  modalOverlay.appendChild(modal);

  document.body.appendChild(modalOverlay);
}


function openModal(event) {
  event.preventDefault(); // previne navegação
  createModal();
}

function addIcon(imgPath, iconClass){
  const icon = document.createElement('img');
  icon.src = imgPath;
  icon.classList.add(iconClass)
  return icon;
}


async function getDashboardData() {
  const produtos = await fetchAllItems();
  const obras = await fetchConstructions();

  return [
    { title: 'Produtos', count: produtos.length },
    { title: 'Obras', count: obras.length },
    { title: 'Relatórios', count: relatorios.length }
  ];
}




