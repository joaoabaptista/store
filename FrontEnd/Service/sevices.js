const API_BASE = 'http://localhost:8080';

  // 1. Obter todos os itens
   export async function fetchAllItems() {
    const response = await fetch(`${API_BASE}/items`);
    return await response.json();
  }

  // 2. Criar novo item
   export async function createNewItem(item) {
    const response = await fetch(`${API_BASE}/newItem`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item)
    });
    return await response.json();
  }

  // 3. Adicionar stock usando itemRef
 export async function addStockByRef(quantity, itemRef) {
    const response = await fetch(`${API_BASE}/add/${quantity}/${itemRef}`, {
      method: 'POST'
    });
    if (!response.ok) throw new Error('Erro ao adicionar stock');
    return await response.json();
  }

  // 4. Remover stock usando itemRef
 export async function subStockByRef(quantity, itemRef) {
    const response = await fetch(`${API_BASE}/sub/${quantity}/${itemRef}`, {
      method: 'POST'
    });
    if (!response.ok) throw new Error('Erro ao remover stock');
    return await response.json();
  }

  // 5. Adicionar item a obra usando itemRef
  export async function addItemToConstructionByRef(constID, itemRef, quantity) {
    const response = await fetch(`${API_BASE}/construction/${constID}/add/${itemRef}/${quantity}`, {
      method: 'POST'
    });
    if (!response.ok) throw new Error('Erro ao adicionar item à obra');
    return await response.json();
  }

  // 6. Remover item de obra usando itemRef
 export async function subItemFromConstructionByRef(constID, itemRef, quantity) {
    const response = await fetch(`${API_BASE}/construction/${constID}/sub/${itemRef}/${quantity}`, {
      method: 'POST'
    });
    if (!response.ok) throw new Error('Erro ao remover item da obra');
    return await response.json();
  }

  // 7. Obter lista de itens da obra
 export async function getConstructionItems(constID) {
    const response = await fetch(`${API_BASE}/construction/${constID}/list`);
    return await response.json();
  }

  // 8. Criar nova obra
  export async function createNewConstruction(construction) {
    const response = await fetch(`${API_BASE}/newConstruction`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(construction)
    });
    return await response.json();
  }

  // 9. Buscar item por itemRef
  export async function getItemByRef(itemRef) {
    const response = await fetch(`${API_BASE}/item/${itemRef}`);
    if (!response.ok) throw new Error('Item não encontrado');
    return await response.json();
  }

  export async function fetchConstructions() {
    try {
      const response = await fetch(`${API_BASE}/constructions`);
      if (!response.ok) {
        throw new Error('Erro ao buscar construções');
      }
  
      const constructions = await response.json();
  
      // Exemplo: mostrar no console
      console.log(constructions);
  
      // Você pode agora renderizar no DOM ou processar os dados como quiser
      return constructions;
    } catch (error) {
      console.error('Erro ao buscar construções:', error);
      return [];
    }
  }
  
