# !/usr/bin/env python3
'''Grammar Learner 0.6 tests 2018-09-29: unittest
Run test:
$ cd language-learning
$ source activate ull
$ python tests/test_grammar_learner.py
'''
#from pathlib import Path
#print('Running' if __name__ == '__main__' else 'Importing', Path(__file__).resolve())

import os, sys
import unittest
from decimal import Decimal

module_path = os.path.abspath(os.path.join('.'))
if module_path not in sys.path: sys.path.append(module_path)
from src.grammar_learner.utl import UTC
from src.grammar_learner.read_files import check_dir
from src.grammar_learner.learner import learn_grammar
from src.grammar_learner.pqa_table import pqa_meter
# from ull.grammartest.optconst import *

#base  = module_path + '/tests/data/POC-Turtle/' + \
#    'generalized_rules/dict_6C_2018-10-03_0006.4.0.dict'

input_parses = module_path + '/tests/data/dataSymbols/dynsym/'
batch_dir = module_path + '/output/test_dynsym_' + str(UTC())[:10]
prj_dir = batch_dir + '/dynsym_rules_b/'
if check_dir(prj_dir, create=True, verbose='max'):
    outpath = prj_dir
kwargs = {
    'input_parses'  :   input_parses,
    'output_grammar':   outpath
}
response = learn_grammar(**kwargs)
with open(response['grammar_file'], 'r') as f:
    rules = f.read().splitlines()
rule_list = [line for line in rules if line[0:1] in ['"', '(']]
#with open(base, 'r') as f: lst = f.read().splitlines()
#base_list = [line for line in lst if line[0:1] in ['"', '(']]
#if len(rule_list) == len(base_list):
#    assert rule_list == base_list
#else:
#    assert len(rule_list) == len(base_list), f"\nlen(rule_list)={len(rule_list)}" \
#                                             f"\nlen(base_list)={len(base_list)}" \
#                                             f"\nrule_list:\n{rule_list}" \
#                                             f"\nbase_list:\n{base_list}"
    # assert len(rule_list) == len(base_list)

