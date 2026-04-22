# Frontend Chat Application

This document outlines the steps to create a frontend application for the chat system. We will use React as the frontend framework.

## 1. Project Setup

First, create a new React application. You can use Create React App for this:

```bash
npx create-react-app frontend
cd frontend
```

## 2. Install Dependencies

Next, install the necessary libraries for WebSocket and STOMP communication, as well as a UUID library to generate unique client IDs:

```bash
npm install @stomp/stompjs sockjs-client uuid
```

## 3. Application Structure

Create the following components inside the `src` directory:

- `AdminChat.js`: The component for the admin's chat interface.
- `UserChat.js`: The component for the user's chat interface.
- `App.js`: The main application component to route between the admin and user views.

## 4. WebSocket Connection (`useChat.js` hook)

Create a custom hook to manage the WebSocket connection. Create a file `src/useChat.js`:

```javascript
import { useEffect, useState, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { v4 as uuidv4 } from 'uuid';

const useChat = (isAdmin) => {
    const [messages, setMessages] = useState([]);
    const [activeSessions, setActiveSessions] = useState([]);
    const [selectedSession, setSelectedSession] = useState(null);
    const clientRef = useRef(null);
    const clientId = useRef(isAdmin ? 'admin' : uuidv4());

    useEffect(() => {
        const client = new Client({
            webSocketFactory: () => new SockJS('http://localhost:8080/websocket-endpoint'),
            connectHeaders: {
                clientId: clientId.current,
                clientName: isAdmin ? 'Admin' : 'User ' + clientId.current.substring(0, 4),
            },
            onConnect: () => {
                if (isAdmin) {
                    client.subscribe('/topic/admin', (message) => {
                        const receivedMessage = JSON.parse(message.body);
                        setMessages((prev) => [...prev, receivedMessage]);
                    });
                    fetchActiveSessions();
                } else {
                    client.subscribe('/topic/user.' + clientId.current, (message) => {
                        const receivedMessage = JSON.parse(message.body);
                        setMessages((prev) => [...prev, receivedMessage]);
                    });
                }
            },
        });

        client.activate();
        clientRef.current = client;

        return () => {
            client.deactivate();
        };
    }, [isAdmin]);

    const fetchActiveSessions = async () => {
        const response = await fetch('http://localhost:8080/admin/chat/sessions');
        const sessions = await response.json();
        setActiveSessions(sessions);
    };

    const fetchChatHistory = async (sessionId) => {
        const response = await fetch(`http://localhost:8080/admin/chat/sessions/${sessionId}/messages`);
        const history = await response.json();
        setMessages(history);
    };

    const sendMessage = (content) => {
        const message = {
            from: isAdmin ? 'Admin' : 'User ' + clientId.current.substring(0, 4),
            content,
            clientId: selectedSession ? selectedSession.clientId : clientId.current,
            admin: isAdmin,
        };
        clientRef.current.publish({ destination: '/app/chat.send', body: JSON.stringify(message) });
    };

    const selectSession = (session) => {
        setSelectedSession(session);
        fetchChatHistory(session.id);
    };

    return { messages, activeSessions, selectedSession, sendMessage, selectSession };
};

export default useChat;
```

## 5. Admin Component (`AdminChat.js`)

Create `src/AdminChat.js`:

```javascript
import React from 'react';
import useChat from './useChat';

const AdminChat = () => {
    const { messages, activeSessions, selectedSession, sendMessage, selectSession } = useChat(true);
    const [input, setInput] = React.useState('');

    const handleSend = () => {
        sendMessage(input);
        setInput('');
    };

    return (
        <div>
            <h1>Admin Chat</h1>
            <div>
                <h2>Active Sessions</h2>
                <ul>
                    {activeSessions.map((session) => (
                        <li key={session.id} onClick={() => selectSession(session)}>
                            {session.clientName} ({session.clientId})
                        </li>
                    ))}
                </ul>
            </div>
            {selectedSession && (
                <div>
                    <h2>Chat with {selectedSession.clientName}</h2>
                    <div>
                        {messages.map((msg, index) => (
                            <div key={index}>
                                <strong>{msg.from}:</strong> {msg.content}
                            </div>
                        ))}
                    </div>
                    <input value={input} onChange={(e) => setInput(e.target.value)} />
                    <button onClick={handleSend}>Send</button>
                </div>
            )}
        </div>
    );
};

export default AdminChat;
```

## 6. User Component (`UserChat.js`)

Create `src/UserChat.js`:

```javascript
import React from 'react';
import useChat from './useChat';

const UserChat = () => {
    const { messages, sendMessage } = useChat(false);
    const [input, setInput] = React.useState('');

    const handleSend = () => {
        sendMessage(input);
        setInput('');
    };

    return (
        <div>
            <h1>User Chat</h1>
            <div>
                {messages.map((msg, index) => (
                    <div key={index}>
                        <strong>{msg.from}:</strong> {msg.content}
                    </div>
                ))}
            </div>
            <input value={input} onChange={(e) => setInput(e.target.value)} />
            <button onClick={handleSend}>Send</button>
        </div>
    );
};

export default UserChat;
```

## 7. Main App Component (`App.js`)

Modify `src/App.js` to allow switching between admin and user views.

```javascript
import React from 'react';
import { BrowserRouter as Router, Route, Link, Routes } from 'react-router-dom';
import AdminChat from './AdminChat';
import UserChat from './UserChat';

function App() {
    return (
        <Router>
            <div>
                <nav>
                    <ul>
                        <li>
                            <Link to="/admin">Admin View</Link>
                        </li>
                        <li>
                            <Link to="/user">User View</Link>
                        </li>
                    </ul>
                </nav>
                <Routes>
                    <Route path="/admin" element={<AdminChat />} />
                    <Route path="/user" element={<UserChat />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
```
You will also need to install `react-router-dom`:
```bash
npm install react-router-dom
```

## 8. Run the application

Start the React application:

```bash
npm start
```

Now you can open two browser windows. In one, navigate to `http://localhost:3000/admin`, and in the other, navigate to `http://localhost:3000/user`. You will be able to send messages from the user to the admin, and the admin will be able to see the user's session and reply.

