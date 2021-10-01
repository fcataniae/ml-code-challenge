CREATE TABLE IF NOT EXISTS  dna_processed (
      id SERIAL PRIMARY KEY,
      dna_sequence VARCHAR UNIQUE NOT NULL,
      mutant BOOLEAN NOT NULL
);