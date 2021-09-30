from db_connection import db
import json

def get_dna_stats(request):

    dna_stats = {}
    with db.connect() as conn:
        sql_query = """
            SELECT COUNT(CASE WHEN mutant THEN 1 END) AS mutant,
            COUNT(CASE WHEN NOT mutant THEN 1 END)  AS human
            FROM dna_processed;
        """
        result_set = conn.execute(sql_query).fetchall()
        mutants = result_set[0][0]
        humans = result_set[0][1]
        ratio = mutants/humans if humans > 0 else 0
        dna_stats = {
            "count_dna_mutant": mutants,
            "count_dna_human": humans,
            "ratio": ratio
        }

    return json.dumps(dna_stats), 200, {'Content-Type': 'application/json'}