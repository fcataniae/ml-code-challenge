import sqlalchemy

connection_name = "rock-loop-327600:us-east1:postgresql-mutants"
db_name = "mutants"
db_user = "mutants-user"
db_password = "franco"
driver_name = 'postgres+pg8000'
query_string =  dict({"unix_sock": "/cloudsql/{}/.s.PGSQL.5432".format(connection_name)})


def create_engine():

    return sqlalchemy.create_engine(
      sqlalchemy.engine.url.URL(
        drivername=driver_name,
        username=db_user,
        password=db_password,
        database=db_name,
        query=query_string,
      ),
      pool_size=5,
      max_overflow=2,
      pool_timeout=30,
      pool_recycle=1800
    )

db = create_engine()