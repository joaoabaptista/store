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
      openPageOverlay(() => resumosPage(constID, items));
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

function openPageOverlay(contentFunction) {
  const overlay = document.createElement('div');
  overlay.classList.add('page-overlay');

  const content = contentFunction(); // função que retorna um elemento DOM com conteúdo da "nova página"
  overlay.appendChild(content);

  const closeButton = document.createElement('button');
  closeButton.textContent = 'Fechar';
  closeButton.classList.add('close-button');
  closeButton.addEventListener('click', () => {
    overlay.remove();
  });

  overlay.appendChild(closeButton);
  document.body.appendChild(overlay);
}


function resumosPage(constID, items) {
  const main = document.createElement('div');
  main.classList.add('resumos-page');

  // Título com número da obra
  const title = document.createElement('h2');
  title.textContent = `Obra nº: ${constID}`;
  main.appendChild(title);

  if (!items || items.length === 0) {
    const noItems = document.createElement('p');
    noItems.textContent = 'Nenhum item encontrado para esta obra.';
    main.appendChild(noItems);
    return main;
  }

  // Criar tabela
  const table = document.createElement('table');
  table.style.width = '100%';
  table.style.borderCollapse = 'collapse';

  // Cabeçalho
  const thead = document.createElement('thead');
  const headerRow = document.createElement('tr');

  ['Referência', 'Descrição', 'Quantidade'].forEach(text => {
    const th = document.createElement('th');
    th.textContent = text;
    th.style.border = '1px solid #000';
    th.style.padding = '8px';
    th.style.backgroundColor = '#eee';
    headerRow.appendChild(th);
  });

  thead.appendChild(headerRow);
  table.appendChild(thead);

  // Corpo da tabela
  const tbody = document.createElement('tbody');

  items.forEach(item => {
    const row = document.createElement('tr');
  
    // Referência (itemRef)
    const refCell = document.createElement('td');
    refCell.textContent = item.itemRef;  // aqui tá a referência, não o id
    refCell.style.border = '1px solid #000';
    refCell.style.padding = '8px';
    row.appendChild(refCell);
  
    // Descrição (name)
    const descCell = document.createElement('td');
    descCell.textContent = item.name;
    descCell.style.border = '1px solid #000';
    descCell.style.padding = '8px';
    row.appendChild(descCell);
  
    // Quantidade (quantity)
    const qtyCell = document.createElement('td');
    qtyCell.textContent = item.quantity;
    qtyCell.style.border = '1px solid #000';
    qtyCell.style.padding = '8px';
    row.appendChild(qtyCell);
  
    tbody.appendChild(row);
  });
  

  table.appendChild(tbody);
  main.appendChild(table);

  return main;
}




