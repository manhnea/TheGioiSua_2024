/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.TheGioiSua_2024.util;

import com.example.TheGioiSua_2024.dto.UserOnlineDto;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrator
 */
public class SessionUserLogin {
  public static Set<UserOnlineDto> onlineUsers = new HashSet<>();
  public static void login(UserOnlineDto UserOnlineDto){
      onlineUsers.add(UserOnlineDto);
  }
  public static void logout(UserOnlineDto UserOnlineDto){
      onlineUsers.remove(UserOnlineDto);
  }
  public static Set<UserOnlineDto> getOnline(){
      return onlineUsers;
 }
}
