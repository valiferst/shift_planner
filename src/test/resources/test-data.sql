-- Insert users into USERX table
INSERT INTO USERX (ID, ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_ID, CREATE_DATE)
VALUES (1000, TRUE, 'Admin', 'Istrator', 'passwd', 'admin', 1000, '2024-01-01 00:00:00');

INSERT INTO USERX (ID, ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_ID, CREATE_DATE)
VALUES (2000, TRUE, 'Susi', 'Kaufgern', 'passwd', 'user1', 1000, '2024-01-01 00:00:00');

INSERT INTO USERX (ID, ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_ID, CREATE_DATE)
VALUES (3000, TRUE, 'Max', 'Mustermann', 'passwd', 'user2', 1000, '2024-01-01 00:00:00');

INSERT INTO USERX (ID, ENABLED, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, CREATE_USER_ID, CREATE_DATE)
VALUES (4000, TRUE, 'Elvis', 'The King', 'passwd', 'elvis', 1000, '2024-01-01 00:00:00');

-- Insert departments into DEPARTMENT table

INSERT INTO DEPARTMENT (
    CLOSING_TIME,
    ID,
    MANAGER_ID,
    OPENING_TIME,
    NAME
) VALUES (
             '2024-01-01 00:00:00',
             2000,
             2000,          -- user1, Susi Kaufgern is a manager
             '2024-01-01 00:00:00',
             'Test Department'
         );

-- Insert shiftplans into SHIFT_PLAN table

INSERT INTO SHIFT_PLAN (CREATE_DATE,
                             CREATE_USER_ID,
                             DATE,
                             DEPARTMENT_ID,
                             END_DATE,
                             ID,
                             START_DATE,
                             UPDATE_DATE,
                             UPDATE_USER_ID,
                             NAME,
                             STATE)
VALUES ('2025-01-14',
        2000, -- user1, Susi Kaufgern
        '2025-01-14',
        2000,
        '2025-12-31',
        2000,
        '2025-01-01',
        '2025-01-15',
        2000,
        'Test Shiftplan',
        'DRAFT');


-- Insert roles into USERX_USERX_ROLE table by looking up the corresponding user ID
INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'admin'), 'ADMIN');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'admin'), 'EMPLOYEE');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'user1'), 'MANAGER');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'user1'), 'EMPLOYEE');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'user2'), 'EMPLOYEE');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'elvis'), 'ADMIN');

INSERT INTO USERX_USERX_ROLE (USERX_ID, ROLES)
VALUES ((SELECT ID FROM USERX WHERE USERNAME = 'elvis'), 'EMPLOYEE');
