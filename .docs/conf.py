# -*- coding: utf-8 -*-

import sys
import os
import sphinx_rtd_theme

needs_sphinx = '1.7'
sys.path.append(os.path.abspath('extensions'))
extensions = ['sphinx.ext.imgmath']
templates_path = ['_templates']
source_suffix = '.rst'
source_encoding = 'utf-8-sig'

master_doc = 'index'
project = 'Shields4J'
copyright = '2019 Oleg Shaburov. Maintained by the TouchBIT Team'
author = 'Oleg Shaburov'
version = 'master'
release = 'latest'
language = 'en'
exclude_patterns = ['_build']
pygments_style = 'monokai'
on_rtd = os.environ.get('READTHEDOCS', None) == 'True'
html_theme = 'sphinx_rtd_theme'
html_theme_path = [sphinx_rtd_theme.get_html_theme_path()]
html_extra_path = ['robots.txt']
html_logo = 'img/logo.jpg'
htmlhelp_basename = 'Shields4J'
file_insertion_enabled = False
linkcheck_anchors = False
linkcheck_timeout = 10

if on_rtd:
    using_rtd_theme = True

html_theme_options = {
    'logo_only': True,
    'collapse_navigation': False,
    'display_version': True,
}