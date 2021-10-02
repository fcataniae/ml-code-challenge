import sqlalchemy
import os

db_host = os.environ["DB_HOST"]
db_port = os.environ["DB_PORT"]
db_name = os.environ["DB_NAME"]
db_user = os.environ["DB_USER"]
db_password = os.environ["DB_PASS"]
driver_name = 'postgres+pg8000'


def create_engine():

    conn_string = f'postgresql+pg8000://{db_user}:{db_password}@{db_host}:{db_port}/{db_name}'

    return sqlalchemy.create_engine(conn_string,
      pool_size=5,
      max_overflow=2,
      pool_timeout=30,
      pool_recycle=1800
    )

db = create_engine()