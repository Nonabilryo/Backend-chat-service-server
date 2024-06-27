package nonabili.chatserviceserver.controller


import nonabili.chatserviceserver.dto.request.ChatMessageRequest
import nonabili.chatserviceserver.entity.Chat
import nonabili.chatserviceserver.service.ChatService
import nonabili.chatserviceserver.util.ResponseFormat
import nonabili.chatserviceserver.util.ResponseFormatBuilder
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@Controller
class ChatController(val chatService: ChatService) {
    @MessageMapping("/{reciver}")
    @SendTo("/topic/{reciver}")
    fun sendMessage(@DestinationVariable reciver: String, chatMessage: ChatMessageRequest): Chat {
        return chatService.saveChat(chatMessage)
    }
    @GetMapping("/chat")
    @ResponseBody
    fun getLetestChats(@RequestHeader requestHeaders: HttpHeaders, @RequestParam(required = false) page: Int = 0): ResponseEntity<ResponseFormat<Any>> {
        val userIdx = requestHeaders.get("userIdx")!![0]
        val result = chatService.getLatestChat(userIdx, page)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.build(result))
    }

    @DeleteMapping("/chat/{chatIdx}")
    @ResponseBody
    fun deleteChat(@RequestHeader requestHeaders: HttpHeaders, @PathVariable chatIdx: Long): ResponseEntity<ResponseFormat<Any>> {
        val userIdx = requestHeaders.get("userIdx")!![0]
        chatService.deleteChat(userIdx, chatIdx)
        return ResponseEntity.ok(ResponseFormatBuilder { message = "success" }.noData())
    }
}

