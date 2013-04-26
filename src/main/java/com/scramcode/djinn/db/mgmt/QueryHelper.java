/*
 * Created on Feb 5, 2006
 * By Fabien Benoit - http://www.scramcode.com
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.scramcode.djinn.db.mgmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class QueryHelper<E> {
   
    public static int callIdentity(Connection conn) {        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("CALL IDENTITY()");
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(rs, stmt);
        }        
    }
    
    public List<E> executeQuery(Connection conn, String sql, RowConverter<E> rowConverter) {
                
        List<E> result = new ArrayList<E>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                result.add( rowConverter.getRow(rs) );
            }            
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(rs, stmt);
        }              
        return result;
    }
    
    public static int executeUpdate(Connection conn, String sql) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            return stmt.executeUpdate(sql);
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(stmt);
        }
    }
    
    public static int executeUpdate(Connection conn, String sql, Object... params) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);            
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i+1, params[i]);
            }            
            return pstmt.executeUpdate();
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(pstmt);
        }
    }
    
    public static int countRows(Connection conn, String table, String whereClause) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT count(*) FROM " + table + " WHERE " + whereClause);
            rs.next();
            return rs.getInt(1);
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(rs, stmt);
        }        
    }

    public static void close(Statement stmt) {
        if(stmt!=null) {
            try {
                stmt.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void close(ResultSet rs) {
        if(rs!=null) {
            try {
                rs.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }  
    
    public static void close(Statement stmt, ResultSet rs) {
        close(stmt);
        close(rs);
    }
    
    public static void close(ResultSet rs, Statement stmt) {
        close(stmt);
        close(rs);
    }
    
    public static void dump(Connection conn, String table) {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM " + table);
            int count = rs.getMetaData().getColumnCount();
            while(rs.next()) {
                for (int i = 0; i < count; i++) {
                    System.out.print(rs.getObject(i+1));
                    if (i < count - 1) {
                        System.out.print("|");    
                    }                    
                }    
                System.out.println();
            }
        }
        catch(SQLException e)  {
            throw new RuntimeException(e);
        }
        finally {
            close(stmt);
        }
    }
    
}
