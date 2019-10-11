package com.bachelor.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bachelor.myUtils.DatabaseConnector;
import com.bachelor.object.appUser;
import com.bachelor.object.feature;
import com.bachelor.object.featureWithValue;
import com.bachelor.object.recommendedItem;
import com.bachelor.object.thisItem;
import com.bachelor.object.topRatedCamera;
import com.bachelor.object.userFeatureValues;


@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		PreparedStatement pstm;
		ResultSet rs;
        try {
        	Class.forName("org.postgresql.Driver");
        	conn = DatabaseConnector.getConnection();
        	Statement stmt = conn.createStatement();
       
            appUser app_user = (appUser) request.getSession().getAttribute("activeAppUser");
            int dbId = app_user.getId();
            String username = app_user.getUsername();
            
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("auid", dbId);
    		
    		rs = stmt.executeQuery("SELECT DISTINCT active_user_id FROM user_features WHERE active_user_id = '" + dbId + "'");
    		String this_id = null;
    		while(rs.next()) {
    			this_id = Integer.toString(rs.getInt("active_user_id"));
    		}
    		
    		if (this_id != null) {
    			
    		} else {
    			stmt.executeUpdate("INSERT INTO user_features(feature_id) SELECT features.id FROM features WHERE showing='true';\r\n" + 
    					"UPDATE user_features SET active_user_id = (SELECT active_user.id FROM active_user WHERE id = '" + dbId +  "') WHERE active_user_id IS NULL");
    			
    		}
    		// LIST FALSE FEATURE
    		List<feature> list_false_feature = new ArrayList<>();
//    		pstm = conn.prepareStatement("SELECT * FROM features WHERE showing='false' AND NOT field_name= 'online_publication' ORDER BY id ASC");
    		pstm = conn.prepareStatement("SELECT * FROM features WHERE id NOT IN (SELECT feature_id FROM user_features WHERE active_user_id='" + dbId +  "') AND NOT field_name= 'online_publication' ORDER BY name ASC");
    		rs = pstm.executeQuery();
    		while (rs.next()) {
    			String false_feature_name = rs.getString("name");
    			String false_field_name = rs.getString("field_name");
    			String false_feature_type = rs.getString("feature_type");
    			boolean false_showing = rs.getBoolean("showing");
    			int false_id = rs.getInt("id");
    			
    			feature feature = new feature();
    			
    			feature.setId(false_id);
    			feature.setFeature_name(false_feature_name);
    			feature.setField_name(false_field_name);
    			feature.setFeature_type(false_feature_type);
    			feature.setShowing(false_showing);
    			list_false_feature.add(feature);
    		}
    		request.setAttribute("listFalseFeature", list_false_feature);
    		
    		// LIST TRUE FEATURE
    		List<feature> list_true_feature = new ArrayList<>();
//    		pstm = conn.prepareStatement("SELECT * FROM features WHERE id IN (SELECT feature_id FROM user_features WHERE user_id='" + dbId +  "') AND NOT field_name= 'online_publication' ORDER BY id ASC");
    		pstm = conn.prepareStatement("SELECT * FROM (SELECT features.name, features.field_name, features.feature_type, features.showing, user_features.* FROM features\r\n"
    				+ "JOIN user_features ON (features.id = user_features.feature_id)) AS t1 WHERE active_user_id='" + dbId +  "'\r\n"
    				+ "AND field_name IN\r\n"  
    				+ "(SELECT field_name FROM user_feature_values, features WHERE active_user_id = '" + dbId + "' AND user_feature_values.feature_id = features.id)" 
    				+ "ORDER BY t1.field_name ASC");
    		rs = pstm.executeQuery();
    		while (rs.next()) {
    			String true_feature_name = rs.getString("name");
    			String true_field_name = rs.getString("field_name");
    			String true_feature_type = rs.getString("feature_type");
    			boolean true_showing = rs.getBoolean("showing");
    			int true_id = rs.getInt("id");
    			
    			feature feature = new feature();
    			
    			feature.setId(true_id);
    			feature.setFeature_name(true_feature_name);
    			feature.setField_name(true_field_name);
    			feature.setFeature_type(true_feature_type);
    			feature.setShowing(true_showing);
    			list_true_feature.add(feature);
    		}
    		
    		// LIST TRUE FEATURE NO VALUE
    		List<feature> list_true_feature_no_value = new ArrayList<>();
    		pstm = conn.prepareStatement("SELECT * FROM (SELECT features.name, features.field_name, features.feature_type, features.showing, user_features.* FROM features\r\n"
    				+ "JOIN user_features ON (features.id = user_features.feature_id)) AS t1 WHERE active_user_id='" + dbId +  "'\r\n"
    				+ "AND field_name NOT IN\r\n"  
    				+ "(SELECT field_name FROM user_feature_values, features WHERE active_user_id = '" + dbId + "' AND user_feature_values.feature_id = features.id)" 
    				+ "ORDER BY t1.field_name ASC");
    		rs = pstm.executeQuery();
    		while (rs.next()) {
    			String true_feature_name = rs.getString("name");
    			String true_field_name = rs.getString("field_name");
    			String true_feature_type = rs.getString("feature_type");
    			boolean true_showing = rs.getBoolean("showing");
    			int true_id = rs.getInt("id");
    			
    			feature feature = new feature();
    			
    			feature.setId(true_id);
    			feature.setFeature_name(true_feature_name);
    			feature.setField_name(true_field_name);
    			feature.setFeature_type(true_feature_type);
    			feature.setShowing(true_showing);
    			list_true_feature_no_value.add(feature);
    		}
    		
    		
    		//LIST OF DISTINCT FEATURE VALUES OF EACH FEATURE
    		List<String> list_field_name = new ArrayList<>();
    		List<String> list_feature_name = new ArrayList<>();
    		List<String> list_feature_type = new ArrayList<>();
     		for (int i = 0; i < list_true_feature.size(); i++) {			
				list_field_name.add(list_true_feature.get(i).getField_name());
				list_feature_name.add(list_true_feature.get(i).getFeature_name());
				list_feature_type.add(list_true_feature.get(i).getFeature_type());
    		}
    		   		
    		//LIST FEATURES WITH VALUES
    		List<featureWithValue> list_feature_with_value = new ArrayList<>();   		
			for (int i = 0; i < list_field_name.size(); i++) {
				featureWithValue feature_with_value = new featureWithValue();
				if (list_feature_type.get(i).equals("TEXT")) {
					List<String> list_distinct = new ArrayList<>();

					pstm = conn.prepareStatement("SELECT DISTINCT (" + list_field_name.get(i)
							+ ") FROM (SELECT item.* FROM item, match_item WHERE item.id = match_item.item_id) AS selected_match "
							+ "WHERE NOT " + list_field_name.get(i) + " ='' AND " + list_field_name.get(i) + " NOT IN "
							+ "(SELECT value FROM user_feature_values WHERE active_user_id = '" + dbId + "' AND feature_id = (SELECT id FROM features WHERE field_name = '" + list_field_name.get(i) + "'))"
							+ "ORDER BY " + list_field_name.get(i) + " ASC");
					rs = pstm.executeQuery();
					while (rs.next()) {
						String distinct_feature_value = rs.getString(1);
						list_distinct.add(distinct_feature_value);
					}
					feature_with_value.setField_name(list_field_name.get(i));
					feature_with_value.setFeature_name(list_feature_name.get(i));
					feature_with_value.setFeature_type(list_feature_type.get(i));
					feature_with_value.setDistinct_value(list_distinct);
					list_feature_with_value.add(feature_with_value);
				} else {
					int min_value = 0;
					int max_value = 0;
					pstm = conn.prepareStatement("SELECT MAX(" + list_field_name.get(i) + ") FROM item, match_item WHERE match_item.item_id = item.id ");
					rs = pstm.executeQuery();
					while (rs.next()) {
						max_value = rs.getInt(1);
					}
					feature_with_value.setField_name(list_field_name.get(i));
					feature_with_value.setFeature_name(list_feature_name.get(i));
					feature_with_value.setFeature_type(list_feature_type.get(i));
					feature_with_value.setMax_value(max_value);
					feature_with_value.setMin_value(min_value);
					list_feature_with_value.add(feature_with_value);
				}
			}  		
    		request.setAttribute("listFeatureWithValue", list_feature_with_value);
    		
    		List<String> list_field_name_no_value = new ArrayList<>();
    		List<String> list_feature_name_no_value = new ArrayList<>();
    		List<String> list_feature_type_no_value = new ArrayList<>();
     		for (int i = 0; i < list_true_feature_no_value.size(); i++) {			
				list_field_name_no_value.add(list_true_feature_no_value.get(i).getField_name());
				list_feature_name_no_value.add(list_true_feature_no_value.get(i).getFeature_name());
				list_feature_type_no_value.add(list_true_feature_no_value.get(i).getFeature_type());
    		}
     		
    		//LIST FEATURES WITH VALUES NO VALUE
    		List<featureWithValue> list_feature_with_value_no_value = new ArrayList<>();   		
			for (int i = 0; i < list_field_name_no_value.size(); i++) {
				featureWithValue feature_with_value_no_value = new featureWithValue();
				if (list_feature_type_no_value.get(i).equals("TEXT")) {
					List<String> list_distinct = new ArrayList<>();
					pstm = conn.prepareStatement("SELECT DISTINCT (" + list_field_name_no_value.get(i)
							+ ") FROM (SELECT item.* FROM item, match_item WHERE item.id = match_item.item_id) AS selected_match "
							+ "WHERE NOT " + list_field_name_no_value.get(i) + " ='' AND " + list_field_name_no_value.get(i) + " NOT IN "
							+ "(SELECT value FROM user_feature_values WHERE active_user_id = '" + dbId + "' AND feature_id = (SELECT id FROM features WHERE field_name = '" + list_field_name_no_value.get(i) + "'))"
							+ "ORDER BY " + list_field_name_no_value.get(i) + " ASC");
					rs = pstm.executeQuery();
					while (rs.next()) {
						String distinct_feature_value = rs.getString(1);
						list_distinct.add(distinct_feature_value);
					}
					feature_with_value_no_value.setField_name(list_field_name_no_value.get(i));
					feature_with_value_no_value.setFeature_name(list_feature_name_no_value.get(i));
					feature_with_value_no_value.setFeature_type(list_feature_type_no_value.get(i));
					feature_with_value_no_value.setDistinct_value(list_distinct);
					list_feature_with_value_no_value.add(feature_with_value_no_value);
				} else {
					int min_value = 0;
					int max_value = 0;
					pstm = conn.prepareStatement("SELECT MAX(" + list_field_name_no_value.get(i) + ") FROM item, match_item WHERE match_item.item_id = item.id");
					rs = pstm.executeQuery();
					while (rs.next()) {
						max_value = rs.getInt(1);
					}
					feature_with_value_no_value.setField_name(list_field_name_no_value.get(i));
					feature_with_value_no_value.setFeature_name(list_feature_name_no_value.get(i));
					feature_with_value_no_value.setFeature_type(list_feature_type_no_value.get(i));
					feature_with_value_no_value.setMax_value(max_value);
					feature_with_value_no_value.setMin_value(min_value);
					list_feature_with_value_no_value.add(feature_with_value_no_value);
				}
			}  		
    		request.setAttribute("listFeatureWithValueNoValue", list_feature_with_value_no_value);

			// ACTIVE USER FEATURE VALUES RATING
			List<userFeatureValues> list_active_user_feature_value = new ArrayList<>();
			pstm = conn.prepareStatement("SELECT user_feature_values.*, features.* FROM user_feature_values, features "
					+ "WHERE active_user_id = ? AND user_feature_values.feature_id = features.id AND user_feature_values.id IN (SELECT user_feature_values_id FROM recently_added WHERE active_user_id = ?)"
					+ "ORDER BY user_feature_values.id DESC");
			pstm.setInt(1, dbId);
			pstm.setInt(2, dbId);
			rs = pstm.executeQuery();			
			while (rs.next()) {
				int id = rs.getInt("id");
				int userid = rs.getInt("active_user_id");
				String value = rs.getString("value");
				String name = rs.getString("name");
				int min = rs.getInt("min");
				int max = rs.getInt("max");
				int rating = rs.getInt("rating");
				userFeatureValues val = new userFeatureValues();
				val.setId(id);
				val.setUser_id(userid);
				val.setValue(value);
				val.setName(name);
				val.setMin(min);
				val.setMax(max);
				val.setRating(rating);
				list_active_user_feature_value.add(val);
			}			
			request.setAttribute("au", list_active_user_feature_value);
			
			// COUNT DISTINCT FEATURES -- TO DISPLAY BUTTON 'SHOW RECOMMENDATIONS' & ALERT
			rs = stmt.executeQuery("SELECT COUNT (DISTINCT feature_id) FROM user_feature_values WHERE active_user_id='" + dbId +"'");
			int feature_count = 0;
			while (rs.next()) {
				feature_count = rs.getInt(1);
			}
			request.getSession().setAttribute("feature_count", feature_count);
			request.getSession().setAttribute("morefeature", 8 - feature_count);
			
			
    		// TOP RATED CAMERAS
    		List<topRatedCamera> list_top_rated_camera = new ArrayList<>();
//    		rs = stmt.executeQuery("SELECT id, product_name, img_src, AVG(rating) AS avg_rating FROM\r\n" + 
//    				"(SELECT item.*, rated_match.amazon_item_id, rated_match.user_id, rated_match.rating, rated_match.timestamp\r\n" + 
//    				"FROM item, (SELECT match_item.item_id, user_item_rating.* FROM match_item, user_item_rating WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id) AS rated_match\r\n" + 
//    				"WHERE item.id = rated_match.item_id) AS view\r\n" + 
//    				"GROUP BY id, product_name, img_src ORDER BY avg_rating DESC LIMIT 10");
    		rs = stmt.executeQuery("SELECT match_item.item_id, AVG(rating) AS avg_rating, item.* \r\n" + 
    				"FROM match_item, user_item_rating, item \r\n" + 
    				"WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id AND item.id = match_item.item_id\r\n" + 
    				"GROUP BY match_item.item_id, item.id ORDER BY avg_rating DESC LIMIT 10");
    		while (rs.next()) {
    			String id = rs.getString("id");
    			String product_name = rs.getString("product_name");
    			int rating = rs.getInt("avg_rating");
    			String img_src = rs.getString("img_src");
    			topRatedCamera camera = new topRatedCamera();
    			camera.setId(id);
    			camera.setProduct_name(product_name);
    			camera.setRating(rating);
    			camera.setImg_src(img_src);
    			list_top_rated_camera.add(camera);
    		}
    		rs.close();
    		request.setAttribute("listTopRatedCamera", list_top_rated_camera);
    		
    		// RECOMMENDED ITEMS
    		List<recommendedItem> list_recommended_item = new ArrayList<>();
    		List<recommendedItem> list_recommended_item_perfect = new ArrayList<>();
    		List<String> list_recommended_item_id = new ArrayList<>();
    		Statement stmt_rec = conn.createStatement();
			ResultSet rs_rec = stmt_rec.executeQuery("SELECT DISTINCT recommended_items.id, view.id AS item_id, product_name, avg_online_price, pixel_number, img_src FROM\r\n" + 
					"(SELECT item.*, rated_match.amazon_item_id, rated_match.user_id, rated_match.rating, rated_match.timestamp\r\n" + 
					"FROM item, (SELECT match_item.item_id, user_item_rating.* FROM match_item, user_item_rating WHERE match_item.amazon_item_id = user_item_rating.amazon_item_id) AS rated_match\r\n" + 
					"WHERE item.id = rated_match.item_id) AS view, recommended_items\r\n" + 
					"WHERE view.id::CHARACTER VARYING = recommended_items.item_id AND active_user_id = '" + dbId + "'\r\n" + 
					"ORDER BY recommended_items.id ASC");
    		while (rs_rec.next()) {
    			String id = rs_rec.getString("item_id");
    			String product_name = rs_rec.getString("product_name");
    			int price = rs_rec.getInt("avg_online_price");
    			int pixel_number = rs_rec.getInt("pixel_number");
    			String img_src = rs_rec.getString("img_src");
    			recommendedItem item = new recommendedItem();
    			item.setId(id);
    			item.setImg_src(img_src);
    			item.setProduct_name(product_name);
    			item.setPrice(price);
    			item.setPixel_number(pixel_number);

    			list_recommended_item_id.add(id);
//    		}   		
    		// PRO AND CON TABLE
				List<userFeatureValues> list_text = new ArrayList<>();
				List<userFeatureValues> list_range = new ArrayList<>();
				List<String> list_field_name_text = new ArrayList<>();
				List<String> list_field_name_range = new ArrayList<>();
				List<String> list_name_text = new ArrayList<>();
				List<String> list_name_range = new ArrayList<>();
				ResultSet rs_procon = stmt.executeQuery("SELECT * FROM user_feature_values, features WHERE active_user_id = '" + dbId
						+ "' AND feature_id = features.id");
				while (rs_procon.next()) {
					String feature_type = rs_procon.getString("feature_type");
					switch (feature_type) {
					case "TEXT":
						String value = rs_procon.getString("value");
						String name_text = rs_procon.getString("name");
						String field_name_text = rs_procon.getString("field_name");
						list_field_name_text.add(field_name_text);
						list_name_text.add(name_text);
//					System.out.println("TEXT: " + name_text);

						userFeatureValues text = new userFeatureValues();
						text.setField_name(field_name_text);
						text.setName(name_text);
						text.setValue(value);
						list_text.add(text);
						break;

					case "CUSTOM_RANGE":
						int min = rs_procon.getInt("min");
						int max = rs_procon.getInt("max");
						String name_range = rs_procon.getString("name");
						String field_name_range = rs_procon.getString("field_name");
						list_field_name_range.add(field_name_range);
						list_name_range.add(name_range);
//					System.out.println("RANGE: " + name_range);

						userFeatureValues range = new userFeatureValues();
						range.setField_name(field_name_range);
						range.setName(name_range);
						range.setMin(min);
						range.setMax(max);
						list_range.add(range);
						break;
					}
				}
				rs_procon.close();
//				System.out.println(list_field_name_text);
//				System.out.println(list_field_name_range);

				List<thisItem> list_this_item_text = new ArrayList<>();
				List<thisItem> list_this_item_number = new ArrayList<>();
				ResultSet rs_procon_1 = stmt.executeQuery("SELECT * FROM item WHERE id = '" + id + "'");
				while (rs_procon_1.next()) {
//					String product_name = rs.getString("product_name");
//					request.getSession().setAttribute("thiscam", product_name);
					for (int i = 0; i < list_field_name_text.size(); i++) {
						String value = rs_procon_1.getString(list_field_name_text.get(i));
						thisItem this_item_text = new thisItem();
						this_item_text.setField_name(list_field_name_text.get(i));
						this_item_text.setText_value(value);
						list_this_item_text.add(this_item_text);
//        			System.out.println(list_field_name_text.get(i) + ": " + value);
					}
					for (int i = 0; i < list_field_name_range.size(); i++) {
						int value = rs_procon_1.getInt(list_field_name_range.get(i));
						thisItem this_item_number = new thisItem();
						this_item_number.setField_name(list_field_name_range.get(i));
						this_item_number.setNumber_value(value);
						list_this_item_number.add(this_item_number);
//        			System.out.println(list_field_name_range.get(i) + ": " + value);
					}
				}
				rs_procon_1.close();
				List<thisItem> list_pro = new ArrayList<>();
				List<thisItem> list_con = new ArrayList<>();
				for (int i = 0; i < list_this_item_text.size(); i++) {
					if (list_this_item_text.get(i).getText_value().equals(list_text.get(i).getValue())) {
						thisItem pro_item = new thisItem();
						pro_item.setField_name(list_text.get(i).getField_name());
						pro_item.setName(list_text.get(i).getName());
						pro_item.setActive_user_value(list_text.get(i).getValue());
						pro_item.setText_value(list_this_item_text.get(i).getText_value());
						list_pro.add(pro_item);
					} else if (list_this_item_text.get(i).getText_value().equals("optional")
							|| list_text.get(i).getValue().equals("optional")
							|| list_this_item_text.get(i).getText_value().equals("eingeschränkt")
							|| list_text.get(i).getValue().equals("eingeschränkt")) {
						thisItem pro_item = new thisItem();
						pro_item.setField_name(list_text.get(i).getField_name());
						pro_item.setName(list_text.get(i).getName());
						pro_item.setActive_user_value(list_text.get(i).getValue());
						pro_item.setText_value(list_this_item_text.get(i).getText_value());
						list_pro.add(pro_item);
					} else if (list_this_item_text.get(i).getText_value().equals("")) {
						thisItem con_item = new thisItem();
						con_item.setField_name(list_text.get(i).getField_name());
						con_item.setName(list_text.get(i).getName());
						con_item.setActive_user_value(list_text.get(i).getValue());
						con_item.setText_value(" - ");
						list_con.add(con_item);
					} else {
						thisItem con_item = new thisItem();
						con_item.setField_name(list_text.get(i).getField_name());
						con_item.setName(list_text.get(i).getName());
						con_item.setActive_user_value(list_text.get(i).getValue());
						con_item.setText_value(list_this_item_text.get(i).getText_value());
						list_con.add(con_item);
					}
				}

				for (int i = 0; i < list_this_item_number.size(); i++) {
					if (list_this_item_number.get(i).getNumber_value() >= list_range.get(i).getMin()
					 && list_this_item_number.get(i).getNumber_value() <= list_range.get(i).getMax()) {
						if (list_this_item_number.get(i).getNumber_value() == 0) {
							thisItem con_item = new thisItem();
							con_item.setField_name(list_range.get(i).getField_name());
							con_item.setName(list_range.get(i).getName());
							con_item.setActive_user_value(list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
							con_item.setText_value(" - ");
							list_con.add(con_item);
						} else {
							thisItem pro_item = new thisItem();
							pro_item.setField_name(list_range.get(i).getField_name());
							pro_item.setName(list_range.get(i).getName());
							pro_item.setActive_user_value(
									list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
							pro_item.setText_value(Integer.toString(list_this_item_number.get(i).getNumber_value()));
							list_pro.add(pro_item);
						}
					} else if (list_this_item_number.get(i).getNumber_value() == 0) {
						thisItem con_item = new thisItem();
						con_item.setField_name(list_range.get(i).getField_name());
						con_item.setName(list_range.get(i).getName());
						con_item.setActive_user_value(list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
						con_item.setText_value(" - ");
						list_con.add(con_item);
					} else {
						thisItem con_item = new thisItem();
						con_item.setField_name(list_range.get(i).getField_name());
						con_item.setName(list_range.get(i).getName());
						con_item.setActive_user_value(list_range.get(i).getMin() + " - " + list_range.get(i).getMax());
						con_item.setText_value(Integer.toString(list_this_item_number.get(i).getNumber_value()));
						list_con.add(con_item);
					}
				}
				
				Iterator<thisItem> iterator = list_con.iterator();
				while (iterator.hasNext()) {
					String field_name_con = (String) iterator.next().getField_name();
					for (int i = 0; i < list_pro.size(); i++) {
						String field_name_pro = (String) list_pro.get(i).getField_name();
						if (field_name_con.equals(field_name_pro)) {
//							System.out.println("CON: " + field_name_con + " AND PRO: " + field_name_pro);
							iterator.remove();
						}
					}
				}
				 
				item.setList_pro(list_pro);
				item.setList_con(list_con);
//				System.out.println(list_pro.size() + " pro vs. con " + list_con.size());
				if (list_con.size() == 0 && list_pro.size() >  0) {
					list_recommended_item_perfect.add(item);
				} else if (list_con.size() != 0) {					
					list_recommended_item.add(item);
				}				
			}
    		
    		if (list_recommended_item.size() != 0) {
				if (list_recommended_item_perfect.size() < 8 && list_recommended_item_perfect.size() >= 0) {
					list_recommended_item = list_recommended_item.subList(0, 8 - list_recommended_item_perfect.size());
				} else {
					list_recommended_item = list_recommended_item.subList(0, 0);
					list_recommended_item_perfect = list_recommended_item_perfect.subList(0, 8);
				}
    		} else {
    			if (list_recommended_item_perfect.size() != 0) {
    				list_recommended_item_perfect = list_recommended_item_perfect.subList(0, 8);
				}
			}
    		
    		System.out.println("PERFECT ITEMS: " + list_recommended_item_perfect.size());
    		System.out.println("OTHER ITEMS: " + list_recommended_item.size());
    		
    		request.setAttribute("listRecommendedItemPerfect", list_recommended_item_perfect);
    		request.setAttribute("listRecommendedItem", list_recommended_item);
    		request.setAttribute("countPerfectItem", list_recommended_item_perfect.size());
    		request.setAttribute("countNormalItem", list_recommended_item.size());
    		
    		
        	//FEATURE RECOMMENDATIONS
        	List<feature> list_recommended_feature = new ArrayList<>();
        	rs = stmt.executeQuery("SELECT recommended_feature.*, features.name, features.field_name\r\n" + 
        			"FROM recommended_feature, features WHERE active_user_id = '" + dbId + "' AND features.id = recommended_feature.feature_id\r\n" + 
        			"ORDER BY predicted_rating DESC LIMIT 8");
        	while (rs.next()) {
        		String feature_name = rs.getString("name");
        		String feature_value = rs.getString("feature_value");
        		String field_name = rs.getString("field_name");
        		int feature_id = rs.getInt("feature_id");
        		feature feature = new feature();
        		feature.setFeature_name(feature_name);
        		feature.setFeature_value(feature_value);
        		feature.setField_name(field_name);
        		feature.setId(feature_id);
        		list_recommended_feature.add(feature);
        	}
        	
        	request.getSession().setAttribute("listRecommendedFeature", list_recommended_feature);
        	
        	request.getRequestDispatcher("home.jsp").forward(request, response);
        	
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
