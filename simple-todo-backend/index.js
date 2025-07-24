const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

const app = express();
const port = 4000;

app.use(cors());
app.use(bodyParser.json());

let users = [{ username: 'admin', password: 'admin' }];
let items = [];

// === LOGIN ===
app.post('/login', (req, res) => {
  const { username, password } = req.body;

  if (!username?.trim() || !password?.trim()) {
    return res.status(400).json({
      success: false,
      message: 'Username and password are required'
    });
  }

  const user = users.find(u => u.username === username && u.password === password);
  if (user) {
    return res.status(200).json({
      success: true,
      message: 'Login successful'
    });
  } else {
    return res.status(401).json({
      success: false,
      message: 'Invalid credentials'
    });
  }
});

// === GET ITEMS ===
app.get('/items', (req, res) => {
  res.status(200).json(items);
});

// === CREATE ITEM ===
app.post('/items', (req, res) => {
  const { text } = req.body;
  if (!text || !text.trim()) {
    return res.status(400).json({ message: 'Text is required' }); // Capital T
  }
  const item = { id: Date.now().toString(), text: text.trim() };
  items.push(item);
  res.status(201).json(item);
});

// === UPDATE ITEM ===
app.put('/items/:id', (req, res) => {
  const { id } = req.params;
  const { text } = req.body;

  const item = items.find(i => i.id === id);
  if (!item) {
    return res.status(404).json({ message: 'Item not found' });
  }
  if (!text || !text.trim()) {
    return res.status(400).json({ message: 'Text is required' }); // Capital T
  }

  item.text = text.trim();
  res.status(200).json(item);
});

// === DELETE ITEM ===
app.delete('/items/:id', (req, res) => {
  const { id } = req.params;
  const index = items.findIndex(i => i.id === id);
  if (index === -1) {
    return res.status(404).json({ message: 'Item not found' });
  }
  items.splice(index, 1);
  res.status(204).send();
});

app.listen(port, () => console.log(`✅ Server running at http://localhost:${port}`));
