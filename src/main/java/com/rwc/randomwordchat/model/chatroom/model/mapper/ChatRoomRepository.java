package com.rwc.randomwordchat.model.chatroom.model.mapper;

import com.rwc.randomwordchat.model.chatroom.model.dto.ChatRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoom> chatRoomMap;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

    // 전체 채팅방 조회
    public List<ChatRoom> findAllRoom() {
        List chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    // roomId로 채팅방 찾기
    public ChatRoom findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    // roomName으로 채팅방 만들기
    public ChatRoom createChatRoom(String roomName) {
        ChatRoom chatRoom = ChatRoom.create(roomName);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    // roomId로 채팅방 삭제하기
    public boolean removeChatRoom(String roomId) {
        if(chatRoomMap.get(roomId).getRoomId().equals(roomId)) {
            chatRoomMap.remove(roomId);
            return true;
        }
        return false;
    }

    // 채팅방 인원 +1
    public void userCntPlus(String roomId) {
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount()+1);
    }

    // 채팅방 인원 -1
    public void userCntMinus(String roomId) {
        ChatRoom room = chatRoomMap.get(roomId);
        room.setUserCount(room.getUserCount()-1);
    }

    // 채팅방에 유저 추가
    public String addUser(String roomId, String userName) {
        ChatRoom room = chatRoomMap.get(roomId);
        String userUUID = UUID.randomUUID().toString();

        if(room.getUserList().containsKey(userUUID)) {
            return null;
        }

        // 아이디 중복 확인 후 userList에 추가
        room.getUserList().put(userUUID, userName);
        return userUUID;
    }

    // 채팅방 유저 이름 중복 확인
    public boolean isDuplicateName(String roomId, String userName) {
        ChatRoom room = chatRoomMap.get(roomId);

        if(room.getUserList().containsValue(userName)) {
            return false;
        }
        return true;
    }

    // 채팅방 유저 리스트 삭제
    public void delUser(String roomId, String userUUID) {
        ChatRoom room = chatRoomMap.get(roomId);
        room.getUserList().remove(userUUID);
    }

    // 채팅방 userName 조회
    public String getUserName(String roomId, String userUUID) {
        ChatRoom room = chatRoomMap.get(roomId);
        return room.getUserList().get(userUUID);
    }

    // 채팅방 전체 userList 조회
    public ArrayList<String> getUserList(String roomId) {
        ArrayList<String> list = new ArrayList<>();

        ChatRoom room = chatRoomMap.get(roomId);

        room.getUserList().forEach((key, value) -> list.add(value));
        return list;
    }
}
