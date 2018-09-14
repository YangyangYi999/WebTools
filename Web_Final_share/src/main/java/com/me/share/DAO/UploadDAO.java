
package com.me.share.DAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.me.share.exception.ContentException;
import com.me.share.pojo.Collect;
import com.me.share.pojo.Comment;
import com.me.share.pojo.Content;
import com.me.share.pojo.User;



public class UploadDAO extends DAO {

	public UploadDAO() {

	}

	public List<Content> get(User user) throws ContentException {
		try {
			begin();
			String q = "";
			q = "from Content where fk_personID= :fk_personID";
			Query q1 = getSession().createQuery(q);
			q1.setLong("fk_personID", user.getPersonID());
			List<Content> content_list = q1.list();
			commit();
			return content_list;
		} catch (HibernateException e) {
			rollback();
			throw new ContentException("Could not get contents from " + user.getFirstName(), e);
		}
	}
	
	public List<Content> getAllContents() throws ContentException {
		  try {
		   begin();
		   String q = "";
		   q = "from Content";
		   Query q1 = getSession().createQuery(q);
		   List<Content> contents = q1.list();
		   commit();
		   return contents;
		  } catch (HibernateException e) {
		   rollback();
		   throw new ContentException("Could not get Content  ", e);
		  }
		 }
	
	public  Long getAllContentsNum() throws ContentException {
		  try {
		   begin();
		   Criteria crit = getSession().createCriteria(Content.class);
		   ProjectionList projList = Projections.projectionList();
		   projList.add(Projections.count("contentID"));
		   crit.setProjection(projList);
		   Long results = (Long) crit.uniqueResult();
		   return results;
		  } catch (HibernateException e) {
		   rollback();
		   throw new ContentException("Could not get Content number  ", e);
		  }
		 }
	
	public Content getContent(long contentID) throws ContentException {
		try {
			begin();
			String q = "";
			q = "from Content where contentID= :contentID";
			Query q1 = getSession().createQuery(q);
			q1.setLong("contentID", contentID);
			Content post = (Content) q1.uniqueResult();
			commit();
			return post;
		} catch (HibernateException e) {
			rollback();
			throw new ContentException("could not find Content with id" + contentID);
		}
	}

	public Content upload(Content p) throws ContentException {
		try {
			begin();
			System.out.println("inside DAO");
			getSession().save(p);
			commit();
			return p;

		} catch (HibernateException e) {
			rollback();
			throw new ContentException("Exception while uploading " + e.getMessage());
		}
	}

//	public void delete(Content c) throws ContentException {
//		try {
//			begin();
//			getSession().delete(c);
//			commit();
//		} catch (HibernateException e) {
//			rollback();
//			throw new ContentException("Could not delete Content ");
//		}
//	}

	public long increment(String contentID) throws ContentException {
		try {
			begin();
			System.out.println("like counter");
			long count = 0;
			long ID = Long.parseLong(contentID);
			String q = "";
			q = "from Content where contentID= :contentID";
			Query q1 = getSession().createQuery(q);
			q1.setLong("contentID", ID);
			Content con = (Content) q1.uniqueResult();
			count = con.getCollectCount() + 1;
			con.setCollectCount(count);
			getSession().update(con);
			getSession().refresh(con);
			System.out.println("updated");
			commit();
			return count;
		} catch (HibernateException e) {
			rollback();
			throw new ContentException("cannot like this pic");
		}
	}

	public Comment addComment(Comment c) throws ContentException {
		try {
			begin();
			getSession().saveOrUpdate(c);
			
				getSession().refresh(c);
			
			commit();
			return c;
		} catch (HibernateException e) {
			rollback();
			throw new ContentException("Exception while adding comment");
		}
	}

	public Collect doCollect(User u, Content c) throws ContentException {
		try {
			begin();
			Criteria crit = getSession().createCriteria(Collect.class);
			crit.add(Restrictions.eq("user", u));
			crit.add(Restrictions.eq("collectContent",c));
			Collect collect = (Collect)crit.uniqueResult();
			commit();
			return collect;
		} catch (HibernateException e) {
			rollback();
			throw new ContentException("Exception while checking like" + c.getContentID());
		}
	}

	public void registerLike(Collect collect) throws ContentException {
		try {
			begin();
			getSession().saveOrUpdate(collect);
			getSession().refresh(collect);
			commit();
		} catch (HibernateException e) {
			throw new ContentException("Exception while liking the photo");
		}

	}

	public void removeLike(Collect c) throws ContentException {
		try{
			begin();
			getSession().delete(c);
			commit();
		}catch(HibernateException e){
			throw new ContentException("Exception while unliking");
		}
	}

	public List<Content> getAllContentsbyPage(int pageNumber) throws ContentException {
		// TODO Auto-generated method stub
		 try {
			   begin();
			   Criteria crit = getSession().createCriteria(Content.class);
			   crit.setFirstResult((pageNumber-1)*5);
			   crit.setMaxResults(5);
			   List<Content> results = crit.list();
			   commit();
			   return results;
			  } catch (HibernateException e) {
			   rollback();
			   throw new ContentException("Could not get Content  ", e);
			  }
			 }
	
}
