import React, { useState } from 'react';

function App() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [items, setItems] = useState([]);
  const [text, setText] = useState('');
  const [editItemId, setEditItemId] = useState(null);
  const [editText, setEditText] = useState('');

  const login = async () => {
    try {
      await axios.post('http://localhost:4000/login', { username, password });
      setIsLoggedIn(true);
      fetchItems();
    } catch (err) {
      alert(err.response?.data?.message || 'Login failed');
    }
  };

  const logout = () => {
    setIsLoggedIn(false);
    setUsername('');
    setPassword('');
    setItems([]);
  };

  const fetchItems = async () => {
    const res = await axios.get('http://localhost:4000/items');
    setItems(res.data);
  };

  const addItem = async () => {
    if (!text.trim()) {
      alert('Item cannot be blank');
      return;
    }
    const res = await axios.post('http://localhost:4000/items', { text });
    setItems([...items, res.data]);
    setText('');
  };

  const deleteItem = async (id) => {
    await axios.delete(`http://localhost:4000/items/${id}`);
    setItems(items.filter(item => item.id !== id));
  };

  const startEdit = (item) => {
    setEditItemId(item.id);
    setEditText(item.text);
  };

  const cancelEdit = () => {
    setEditItemId(null);
    setEditText('');
  };

  const saveEdit = async (id) => {
    if (!editText.trim()) {
      alert('Text cannot be empty');
      return;
    }
    const res = await axios.put(`http://localhost:4000/items/${id}`, { text: editText });
    setItems(items.map(item => item.id === id ? res.data : item));
    cancelEdit();
  };

  return isLoggedIn ? (
    <div>
      <h2>Todo Items</h2>
      <button onClick={logout}>Logout</button>
      <br /><br />
      <input
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Add item"
      />
      <button onClick={addItem}>Add</button>
      <ul>
        {items.map(item => (
          <li key={item.id}>
            {editItemId === item.id ? (
              <>
                <input
                  value={editText}
                  onChange={(e) => setEditText(e.target.value)}
                />
                <button onClick={() => saveEdit(item.id)}>Save</button>
                <button onClick={cancelEdit}>Cancel</button>
              </>
            ) : (
              <>
                {item.text}
                <button onClick={() => startEdit(item)}>Edit</button>
                <button onClick={() => deleteItem(item.id)}>Delete</button>
              </>
            )}
          </li>
        ))}
      </ul>
    </div>
  ) : (
    <div>
      <h2>Login</h2>
      <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="Username" />
      <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" />
      <button onClick={login}>Login</button>
    </div>
  );
}

export default App;