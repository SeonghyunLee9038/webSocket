package practie.websocket.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.*

import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectedEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import practie.websocket.model.ChatMessage
import practie.websocket.model.MessageType


@Component
class WebSocketEventListener {

    @Autowired
    lateinit var messagingTemplate: SimpMessageSendingOperations

    @EventListener
    fun handleWebSocketConnectListener(event: SessionConnectedEvent) {
        println("Received a new web socket connection")
    }

    @EventListener
    fun handlerWebSocketDisconnectionListener(event : SessionDisconnectEvent){
        var headerAccessor : StompHeaderAccessor = StompHeaderAccessor.wrap(event.message)

        var username : String = headerAccessor.sessionAttributes?.get("username") as String
        if(username != null){
            println("User Disconnected : $username")

           var chatMessage :  ChatMessage = ChatMessage()
            chatMessage.type = MessageType.LEAVE
            chatMessage.sender = username

            messagingTemplate.convertAndSend("/topic/public", chatMessage)
        }
    }
}