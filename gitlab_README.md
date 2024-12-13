# GitLab Project Setup
## Branches
The repository is set up to follow the branching policy described in the lecture.
Branches have the following structure:
- main
    - protected branch
    - can not be pushed to
    - only develop can be merged into main
    - merging requires approval by one other maintainer
- develop
    - protected branch
    - can not be pushed to 
    - all branches can be merged into develop
    - merging requires approval by one other maintainer
- feature/.*
    - used for development of features
- fix/.*
    - used for fixing bugs
- upload/.*
    - used for uploading files 

## Pushing
- commit author must use UIBK email
- new branches must have one of the following prefixes
    - feature/
    - fix/
    - upload/
- pushing secret files, e.g. private keys, is disabled

## Merging
- Merging into `develop` and `main` requires approval by one other group member

## Workflow
### Create Merge Request (MR)
- select issue you want to work on
- on `Create merge request` enter
    - Branch name: 
        - `feature/<your_feature>` or 
        - `fix/<your_fix>` or
        - `upload/<upload_reason>`
    - Source (branch or tag):
        - `develop` for features or uploads
        - `develop` or `feature` for fixes
    - now the MR and issue are linked
        - MR is named `Draft: Resolve "<issue_name>"`
        - `closes #<issue_number>` in MR description
        - `mentioned in merge request !<mr_number>` in issue activity
    - assign the MR to yourself
    - save MR

### Setup local branch & push
- setup local branch to track remote branch of merge request
    - type `git fetch`
    - list all remote branches with `git branch -r`
    - type `git switch <your remote branch>` **without the `origin/` prefix**
- `add` and `commit` your changes
- use `git push` to push your changes into the remote repository
    - since tracking is set up you don't need to specify a target

### Merging the MR
- click `Edit` in gitlab
- uncheck `Mark as draft`
    - changes MR status to `Ready`
    - changes MR name to `Resolve "<issue_name>"`
- MR can be merged after 
    - pipeline ran without errors
    - Approval by 1 team member is given
