package com.tyss.gmail.main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import com.tyss.gmail.dao.AccountDaoImpl;
import com.tyss.gmail.dto.AccountDto;
import com.tyss.gmail.dto.Inbox;

public class GmailMainTest {

	public static void main(String[] args) {
		AccountDaoImpl accountDao=new AccountDaoImpl();
		try {
			BufferedReader input=new BufferedReader(new InputStreamReader(System.in));
			String continueOperation=null;
			do {
				System.out.println("Press 1  for Login");
				System.out.println("Press 2 for Register");
				int choice=Integer.parseInt(input.readLine());
				switch (choice) {
				case 1:
					System.out.println("Enter Email");
					String emial=input.readLine();
					System.out.println("Enter Password");
					String password=input.readLine();
					boolean loginUser=accountDao.loginUser(emial, password);
					if(loginUser) {
						String continueWithLogin=null;
						do {
							System.out.println("Press A for Compose");
							System.out.println("Press B for Show Inbox");
							char choise=input.readLine().charAt(0);
							Inbox inbox=new Inbox();
							switch (choise) {
							case 'A':
								System.out.println("Enter Email.");
								String email=input.readLine();
								System.out.println("Enter message.");
								inbox.setMessage(input.readLine());
								boolean composeMail=accountDao.composeMail(inbox, email);
								if(composeMail) {
									System.out.println("Mail Sent");
								}else {
									System.out.println("Mail not Sent");
								}
								break;
							case 'B':
								List<Inbox> inboxList=accountDao.showAllInbox();
								for(Inbox inboxMail:inboxList) {
									System.out.println("Message..:"+inboxMail.getMessage());
								}
								break;

							default:
								System.out.println("Invalid Option");
								break;
							}
							System.out.println("Do you want to Keep Login ? Press Y for Continue : Press N for LogOut.");
							continueWithLogin=input.readLine();
							if(continueWithLogin.charAt(0)=='N') {
								break;
							}
						}while(continueWithLogin.charAt(0)=='Y'||continueWithLogin.charAt(0)=='y');

					}else {
						System.out.println("Invalid user");
					}
					break;
				case 2:
					AccountDto accountDto=new AccountDto();
					System.out.println("Enter UserName.");
					accountDto.setUserName(input.readLine());
					System.out.println("Enter Password.");
					accountDto.setPassword(input.readLine());
					System.out.println("Enter Gmail.");
					accountDto.setEmail(input.readLine());
					boolean registerUser=accountDao.registerUser(accountDto);
					if(registerUser) {
						System.out.println("User is Registered");
					}else {
						System.out.println("User is not Registered");
					}
					break;
				default:
					System.out.println("Invalid option.");
					break;
				}

				System.out.println("Press Y for continue and Press N for Exit");
				continueOperation=input.readLine();
				if(continueOperation.charAt(0)=='N') {
					break;
				}
			}while(continueOperation.charAt(0)=='Y'||continueOperation.charAt(0)=='y');

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
