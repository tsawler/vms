.	//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-05-14
//Revisions
//------------------------------------------------------------------------------
//$Log: Readme.txt,v $
//Revision 1.9.2.2.14.1  2007/06/11 15:37:21  tcs
//*** empty log message ***
//
//Revision 1.9.2.2  2004/12/10 14:59:53  tcs
//*** empty log message ***
//
//Revision 1.9.2.1  2004/12/08 17:41:02  tcs
//Added -O option to pg_dump, so no owner is attached to sql
//
//Revision 1.9  2004/11/06 11:51:07  tcs
//improved docs
//
//
//Revision 1.6  2004/07/22 15:43:34  tcs
//Changed export to be to binary format (because of bytea cols)
//
//Revision 1.3  2004/07/06 16:47:24  tcs
//Updated instructions
//
//------------------------------------------------------------------------------

==================================
To back up a schema structure only
==================================

	pg_dump -U pgsql -c -F p -n rdogs -s -v -O verilion > skylos_structure_only.sql
	
	i.e.
	
	pg_dump -U <postgres user> -c -F p -n <schema> -s -v -O <database> > <file>

==================================
To back up a schema 
==================================

(i.e one named rdogs) execute this as the db user:

	pg_dump -U pgsql -d -F p -O -v -n rdogs verilion > skylos_structure_and_data.sql
	
	i.e.
	
	pg_dump -U <postgres user> -d -F p -O -v -n <schema> <database>  > <file>
	
==================================
To restore a schema 
==================================

To restore a schema:

	psql -U pgsql -f verilion.sql -d verilion
	
	i.e.
	
	psql -U <postgres user> -f <file> -d <database>
	
	
===================================
To create a new schema for a client
===================================
Do this as pgsql

1) create the schema name
2) load data as follows: psql -U postgres -f verilion.sql -d databasename
3) deploy their app in tomcat
5) ensure the schema/server name are correct in the System Preferences table
6) log on to the admin tool and delete all uncessary pages
7) vaccuum database