import { fetchAllItems, fetchConstructions } from "../Service/sevices.js";

function createElement(type, options = {}) {
  const el = document.createElement(type);
  if (options.className) el.className = options.className;
  if (options.text) el.textContent = options.text;
  if (options.html) el.innerHTML = options.html;
  if (options.attrs) {
    for (const attr in options.attrs) {
      el.setAttribute(attr, options.attrs[attr]);
    }
  }
  if (options.events) {
    for (const ev in options.events) {
      el.addEventListener(ev, options.events[ev]);
    }
  }
  return el;
}

async function getDashboardData() {
  const produtos = await fetchAllItems();
  const obras = await fetchConstructions();
  const relatorios = []; // ou buscar de algum endpoint

  return [
    { title: 'Produtos', count: produtos.length },
    { title: 'Obras', count: obras.length },
    { title: 'Relatórios', count: relatorios.length }
  ];
}

function init() {
  const container = createElement('div', { className: 'container' });
  document.body.appendChild(container);

  // Sidebar
  const sidebar = createElement('nav', { className: 'sidebar' });
  const logo = createElement('div', { className: 'logo', text: 'SISTEMA' });
  sidebar.appendChild(logo);

  const menuItems = [
    { name: 'Produtos', icon: '🏷️' },
    { name: 'Obras', icon: '🏗️' },
    { name: 'Configurações', icon: '⚙️' }
  ];

  menuItems.forEach((item, i) => {
    const link = createElement('a', {
      text: item.name,
      attrs: { 'href': '#', 'data-icon': item.icon },
      className: i === 0 ? 'active' : '',
      events: {
        click: (e) => {
          e.preventDefault();
          alert(`Navegar para ${item.name}`);
          [...sidebar.querySelectorAll('a')].forEach(a => a.classList.remove('active'));
          link.classList.add('active');
        }
      }
    });
    sidebar.appendChild(link);
  });

  container.appendChild(sidebar);

  // Main content
  const main = createElement('main', { className: 'content' });

  // Header
  const header = createElement('header', { className: 'main-header' });
  const title = createElement('h1', { text: 'Bem-vindo ao Sistema' });
  const userInfo = createElement('div');
  header.appendChild(title);
  header.appendChild(userInfo);
  main.appendChild(header);

  // Stats Section
  const stats = createElement('section', { className: 'stats' });
  main.appendChild(stats);

  // Carregar dados do dashboard e criar cards dentro de stats
  getDashboardData()
    .then(cardsData => {
      stats.innerHTML = ''; // limpa antes de criar

      cardsData.forEach(cardInfo => {
        const card = createElement('div', {
          className: 'card',
          events: { click: () => alert(`Ir para ${cardInfo.title}`) }
        });
        const h2 = createElement('h2', { text: cardInfo.title });
        const p = createElement('p', { text: cardInfo.count });
        card.appendChild(h2);
        card.appendChild(p);
        stats.appendChild(card);
      });
    })
    .catch(err => {
      console.error('Erro ao carregar dados do dashboard:', err);
      stats.appendChild(createElement('p', {
        text: 'Erro ao carregar dados do dashboard.',
        className: 'text-red-600'
      }));
    });

  container.appendChild(main);
}

window.onload = init;
