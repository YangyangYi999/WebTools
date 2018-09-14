package com.me.share.DAO;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.me.share.exception.FollowerException;
import com.me.share.exception.UserException;
import com.me.share.pojo.User;



public class FollowerDAO extends DAO {

	public FollowerDAO() {
	}

	public List<BigInteger> getFollowersbyUser(User u) throws FollowerException {
		try {
			List<BigInteger> followerList = null;
			begin();
			Query q = getSession().createSQLQuery("SELECT fk_personID FROM share.user_follower_table WHERE fk_followerID= :followerID");
			q.setLong("followerID", u.getPersonID());
			followerList = q.list();
			commit();
			return followerList;
		} catch (HibernateException e) {
			rollback();
			throw new FollowerException("Exception while retrieving follower list" + e.getMessage());
		}
	}

	public User get(int userId) throws UserException {
		try {
			begin();
			Query q = getSession().createQuery("from User where personID= :personID");
			q.setInteger("personID", userId);
			User user = (User) q.uniqueResult();
			commit();
			return user;
		} catch (HibernateException e) {
			rollback();
			throw new UserException("Could not get user " + userId, e);
		}
	}

	public boolean addFollower(User followee,User follower) throws FollowerException {
		boolean flag = false;
		try {
			begin();
//			getSession().merge(followee);
			Query q = getSession().createSQLQuery("insert into share.user_follower_table (fk_personID, fk_followerID) values (:personID,:followerID)");
			q.setLong("personID", followee.getPersonID());
			q.setLong("followerID", follower.getPersonID());
			int u = q.executeUpdate();
			if(u == 1){
			System.out.print("inside dao");
			}
			commit();
			flag = true;
			
		} catch (HibernateException e) {
			rollback();
			throw new FollowerException("cannot add follower" + e.getMessage());

		}
		return flag;
	}

	public boolean removeFollower(User followee,User follower) throws FollowerException {
		boolean flag = true;
		try {
			begin();
			Query q = getSession().createSQLQuery("DELETE FROM share.user_follower_table WHERE fk_personID= :personID and fk_followerID= :followerID");
					q.setLong("personID", followee.getPersonID());
					q.setLong("followerID", follower.getPersonID());
			int up = q.executeUpdate();
			if(up >=1){
			System.out.println("removed successful");
			commit();
			}
			System.out.println("commited");
			flag = false;
		} catch (HibernateException e) {
			rollback();
			throw new FollowerException("cannot remove follower" + e.getMessage());
		}
		return flag;
	
	}

}
