import { getConstructionItems, getItemByRef, subItemFromConstructionByRef, addItemToConstructionByRef } from "../Service/sevices.js";

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
    openPageOverlay(searchItemPage);
  });
  
  const stockIcon = addIcon('Resouces/items.png', 'stock_icon');

  const guiasDropdown = createGuiasDropdown();
  


  stock_btn.appendChild(stockIcon);


  bar.appendChild(stock_btn);
  bar.appendChild(guiasDropdown);
  

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
    qtyCell.textContent = item.quantity;/**<----------------------------------------------------------------------------------------------------------------------- */
    qtyCell.style.border = '1px solid #000';
    qtyCell.style.padding = '8px';
    row.appendChild(qtyCell);
  
    tbody.appendChild(row);
  });
  

  table.appendChild(tbody);
  main.appendChild(table);

  return main;
}

function searchItemPage() {
  const container = document.createElement('div');
  container.classList.add('search-item-page');

  const title = document.createElement('h2');
  title.textContent = 'Buscar Item por Referência';
  container.appendChild(title);

  const input = document.createElement('input');
  input.placeholder = 'Digite a referência do item';
  input.classList.add('item-input');
  container.appendChild(input);

  const searchBtn = document.createElement('button');
  searchBtn.textContent = 'Buscar';
  searchBtn.classList.add('search-button');
  container.appendChild(searchBtn);

  const resultContainer = document.createElement('div');
  resultContainer.classList.add('item-result');
  container.appendChild(resultContainer);

  searchBtn.addEventListener('click', async () => {
    const ref = input.value.trim();
    if (!ref) {
      alert('Digite uma referência!');
      return;
    }

    resultContainer.innerHTML = ''; // Limpa antes da nova busca

    try {
      const item = await getItemByRef(ref);

      // Exibe as informações do item
      const refEl = document.createElement('p');
      refEl.textContent = `Referência: ${item.itemRef}`;

      const nameEl = document.createElement('p');
      nameEl.textContent = `Descrição: ${item.name}`;

      const qtyEl = document.createElement('p');
      qtyEl.textContent = `Quantidade: ${item.quantity}`;

      resultContainer.appendChild(refEl);
      resultContainer.appendChild(nameEl);
      resultContainer.appendChild(qtyEl);

    } catch (error) {
      console.error('Erro ao buscar item:', error);
      resultContainer.textContent = 'Item não encontrado.';
    }
  });

  return container;
}

function createGuiasDropdown() {
  const dropdownContainer = document.createElement('div');
  dropdownContainer.classList.add('dropdown');

  const guiasButton = document.createElement('button');
  guiasButton.classList.add('guias-button');
  const guiasIcon = addIcon('Resouces/guias.png', 'stock_icon');
  guiasButton.appendChild(guiasIcon);

  const dropdownMenu = document.createElement('div');
  dropdownMenu.classList.add('dropdown-menu');
  dropdownMenu.style.display = 'none';

  // Cria opções do menu
  const novaGuiaOption = createDropdownItem('Guia de obra', () => {
    console.log('Nova Guia selecionada');
    dropdownMenu.style.display = 'none';
    openPageOverlay(GuiaTransportePage);
  });

  const consultarGuiaOption = createDropdownItem('Guia de devoluçao', () => {
    console.log('Consultar Guias selecionada');
    dropdownMenu.style.display = 'none';
    openPageOverlay(GuiaDevoluçaoPage);
  });

  dropdownMenu.appendChild(novaGuiaOption);
  dropdownMenu.appendChild(consultarGuiaOption);

  dropdownContainer.appendChild(guiasButton);
  dropdownContainer.appendChild(dropdownMenu);

  // Eventos de hover
  dropdownContainer.addEventListener('mouseenter', () => {
    dropdownMenu.style.display = 'block';
  });

  dropdownContainer.addEventListener('mouseleave', () => {
    dropdownMenu.style.display = 'none';
  });

  return dropdownContainer;
}

function createDropdownItem(label, onClick) {
  const item = document.createElement('div');
  item.textContent = label;
  item.classList.add('dropdown-item');
  item.addEventListener('click', onClick);
  return item;
}

function GuiaDevoluçaoPage() {
  const container = document.createElement('div');
  container.classList.add('guia-devolucao-page');

  const title = document.createElement('h2');
  title.textContent = 'Guia de Devolução';
  container.appendChild(title);

  const form = document.createElement('form');
  form.id = 'devolucaoForm';

  const obraInput = document.createElement('input');
  obraInput.type = 'text';
  obraInput.id = 'constID';
  obraInput.placeholder = 'Número da obra';
  obraInput.required = true;
  form.appendChild(obraInput);

  const linesContainer = document.createElement('div');
  linesContainer.id = 'linesContainer';
  form.appendChild(linesContainer);

  const addLineBtn = document.createElement('button');
  addLineBtn.type = 'button';
  addLineBtn.textContent = 'Adicionar Item';
  addLineBtn.addEventListener('click', () => addLine());
  form.appendChild(addLineBtn);

  const submitBtn = document.createElement('button');
  submitBtn.type = 'submit';
  submitBtn.textContent = 'Enviar Devolução';
  form.appendChild(submitBtn);

  container.appendChild(form);

  // Define addLine e removeLine dentro da função principal para manter encapsulamento
  function addLine(ref = '', quantity = '') {
    const line = document.createElement('div');
    line.className = 'line';
    line.innerHTML = `
      <input type="text" name="ref" placeholder="Referência do item" value="${ref}" required />
      <input type="number" name="quantity" placeholder="Quantidade" value="${quantity}" required min="1" />
      <button type="button">🗑️</button>
    `;

    line.querySelector('button').addEventListener('click', () => {
      line.remove();
    });

    linesContainer.appendChild(line);
  }

  form.addEventListener('submit', async function (e) {
    e.preventDefault();

    const constID = obraInput.value.trim();
    const refs = form.querySelectorAll('input[name="ref"]');
    const quantities = form.querySelectorAll('input[name="quantity"]');

    for (let i = 0; i < refs.length; i++) {
      const itemRef = refs[i].value.trim();
      const quantity = quantities[i].value.trim();

      if (!itemRef || !quantity) continue;

      try {
        await subItemFromConstructionByRef(constID, itemRef, quantity);
      } catch (err) {
        alert(`Erro ao devolver item ${itemRef}: ${err.message}`);
      }
    }

    alert('Todos os itens foram enviados com sucesso!');

    // Busca novamente os dados da obra
    try {
      const items = await getConstructionItems(constID);
      openPageOverlay(() => resumosPage(constID, items));
    } catch (err) {
      console.error('Erro ao buscar nova lista da obra:', err);
      alert('Itens devolvidos, mas houve erro ao recarregar a obra.');
    }
      });

  // Linha inicial
  addLine();

  return container;
}

function GuiaTransportePage() {
  const container = document.createElement('div');
  container.classList.add('guia-transporte-page');

  const title = document.createElement('h2');
  title.textContent = 'Guia de Transporte';
  container.appendChild(title);

  const form = document.createElement('form');
  form.id = 'transporteForm';

  const obraInput = document.createElement('input');
  obraInput.type = 'text';
  obraInput.id = 'constID';
  obraInput.placeholder = 'Número da obra';
  obraInput.required = true;
  form.appendChild(obraInput);

  const linesContainer = document.createElement('div');
  linesContainer.id = 'linesContainer';
  form.appendChild(linesContainer);

  const addLineBtn = document.createElement('button');
  addLineBtn.type = 'button';
  addLineBtn.textContent = 'Adicionar Item';
  addLineBtn.addEventListener('click', () => addLine());
  form.appendChild(addLineBtn);

  const submitBtn = document.createElement('button');
  submitBtn.type = 'submit';
  submitBtn.textContent = 'Enviar Transporte';
  form.appendChild(submitBtn);

  container.appendChild(form);

  // Função para adicionar linha de item
  function addLine(ref = '', quantity = '') {
    const line = document.createElement('div');
    line.className = 'line';
    line.innerHTML = `
      <input type="text" name="ref" placeholder="Referência do item" value="${ref}" required />
      <input type="number" name="quantity" placeholder="Quantidade" value="${quantity}" required min="1" />
      <button type="button">🗑️</button>
    `;

    line.querySelector('button').addEventListener('click', () => {
      line.remove();
    });

    linesContainer.appendChild(line);
  }

  // Envio do formulário
  form.addEventListener('submit', async function (e) {
    e.preventDefault();

    const constID = obraInput.value.trim();
    const refs = form.querySelectorAll('input[name="ref"]');
    const quantities = form.querySelectorAll('input[name="quantity"]');

    for (let i = 0; i < refs.length; i++) {
      const itemRef = refs[i].value.trim();
      const quantity = quantities[i].value.trim();

      if (!itemRef || !quantity) continue;

      try {
        await addItemToConstructionByRef(constID, itemRef, quantity); // ← esta função precisas ter no backend
      } catch (err) {
        alert(`Erro ao transportar item ${itemRef}: ${err.message}`);
      }
    }

    alert('Todos os itens foram transportados com sucesso!');

    // Recarrega os dados da obra
    try {
      const items = await getConstructionItems(constID);
      openPageOverlay(() => resumosPage(constID, items));
    } catch (err) {
      console.error('Erro ao buscar nova lista da obra:', err);
      alert('Itens transportados, mas houve erro ao recarregar a obra.');
    }
  });

  // Linha inicial
  addLine();

  return container;
}
