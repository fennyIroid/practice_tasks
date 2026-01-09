const { Server } = require("socket.io");
const { createServer } = require("http");

const httpServer = createServer((req, res) => {
    res.writeHead(200);
    res.end("Server is running");
});

const io = new Server(httpServer, {
    cors: { origin: "*" }
});

io.on("connection", (socket) => {
    console.log("✅ Android connected: " + socket.id);

    socket.on("send_message", (msg) => {
        console.log("📩 Received:", msg);
        socket.emit("receive_message", "Echo: " + msg);
    });
});

// Explicitly bind to 0.0.0.0 to allow access from Android Emulator (10.0.2.2)
httpServer.listen(3000, "0.0.0.0", () => {
    console.log("🚀 Server running on http://0.0.0.0:3000");
});
