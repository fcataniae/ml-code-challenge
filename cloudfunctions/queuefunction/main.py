from db_connection import db
from sqlalchemy.dialects import postgresql
from sqlalchemy import table, column
import base64
import json

def process_insert_event(event, context):

    data = base64.b64decode(event['data']).decode('utf-8')
    data = json.loads(data)
    dna_sequence = data["dnaSequence"]
    mutant = data["mutant"]
    with db.connect().execution_options(autocommit=True)  as conn:
        sql = f"insert into dna_processed (dna_sequence, mutant) values ('{dna_sequence}', {mutant}) on conflict do nothing"
        conn.execute(sql)
    return 'OK', 200
