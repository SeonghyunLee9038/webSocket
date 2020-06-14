package practie.websocket.model

class ChatMessage {

    lateinit var type : MessageType
    var content : String = ""
    var sender : String = ""

}