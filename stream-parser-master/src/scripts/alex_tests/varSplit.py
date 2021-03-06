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
    corpus = corpusdir+v+".txt"
    dictsdir = path+"/"+filename+"/dicts/"
    dicts = dictsdir+v+".vocab"
    parsedir = path+"/"+filename+"/parses/"+v
    scoresdir = path+"/"+filename+"/scores/"+v+"/"
    scores = scoresdir+v

    subprocess.call(['mkdir','-p',corpusdir])
    subprocess.call(['mkdir','-p',dictsdir])
    subprocess.call(['mkdir','-p',parsedir])
    subprocess.call(['mkdir','-p',scoresdir])

    with open(corpus, 'w') as f:
        f.write(items)

    subprocess.call(['./dictionary.sh',corpus,dicts])
    subprocess.call(['./varSplit.sh',dicts,parsedir,corpusdir,scores])




