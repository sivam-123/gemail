package com.tyss.gmail.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tyss.gmail.dto.AccountDto;
import com.tyss.gmail.dto.Inbox;

public class AccountDaoImpl implements AccountDao{
	EntityManagerFactory entityManagerFactory=null;
	EntityManager entityManager=null;
	EntityTransaction transaction=null;

	public AccountDaoImpl() {
		entityManagerFactory=Persistence.createEntityManagerFactory("TestPersistence");
		entityManager=entityManagerFactory.createEntityManager();
		transaction=entityManager.getTransaction();	
	}

	public boolean registerUser(AccountDto accountDto) {
		try {
			transaction.begin();
			entityManager.persist(accountDto);
			transaction.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean loginUser(String email, String password) {
		Query query=entityManager.createQuery("from AccountDto where email=:email and password=:password");
		query.setParameter("email",email);
		query.setParameter("password",password);
		AccountDto accountDto=(AccountDto)query.getSingleResult();
		if(accountDto!=null) {
			return true;
		}else {
			return false;
		}
	}

	public boolean composeMail(Inbox inbox, String email) {
		Query query=entityManager.createQuery("from AccountDto where email=:email");
		query.setParameter("email",email);
		AccountDto account=(AccountDto)query.getSingleResult();
		int userId=account.getUserId();
		inbox.setUserId(userId);
		try {
			transaction.begin();
			entityManager.persist(inbox);
			transaction.commit();
			return true;
		} catch (Exception e) {
			transaction.rollback();
			return false;	
		}	
	}

	public List<Inbox> showAllInbox() {
		Query query=entityManager.createQuery("from Inbox");
		return query.getResultList();
	}
	@Override
	protected void finalize() throws Throwable {
		if(entityManager!=null) {
			entityManager.close();
		}
		if(entityManagerFactory!=null) {
			entityManagerFactory.close();
		}
	}
}
