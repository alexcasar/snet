# !/usr/bin/env python3

import os
import numpy as np
import pandas as pd
import sys  
import subprocess
import subprocess

path = sys.argv[1]
filename = sys.argv[2]
doc = pd.read_csv(path+"/"+filename+"/"+filename+"_sym.csv")  

varnames = doc.columns

for v in varnames[:-1]:
    item = list(doc[v])
    items = ' '.join(item)
    corpusdir = path+"/"+filename+"/corpus/"+v+"/"
    corpus = corpusdir+v
    dicts = path+"/"+filename+"/dicts/"+v+".vocab"
    parsedir = path+"/"+filename+"/parse/"+v
    scores = path+"/"+filename+"/scores/"+v+".fmi"

    # Create target Directory if don't exist
    if not os.path.exists(corpusdir):
        os.mkdir(corpusdir)
        print("Directory " , corpusdir ,  " Created ")
    else:    
        print("Directory " , corpusdir ,  " already exists")

    if not os.path.exists(parsedir):
        os.mkdir(parsedir)
        print("Directory " , parsedir ,  " Created ")
    else:    
        print("Directory " , parsedir ,  " already exists")

    with open(corpus, 'w') as f:
        f.write(items)
    subprocess.call(['./dictionary.sh',corpus,dicts])
    subprocess.call(['./corpus_parse.sh',dicts,corpusdir,parsedir,scores])




