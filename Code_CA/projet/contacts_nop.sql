create temp table _variables(last_rowid_contact integer, last_rowid_groupe integer);
insert into groupe (nom) values('nop');
insert into _variables (last_rowid_groupe) values ((select last_insert_rowid() from groupe));

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 't' ,1, 'test',2016-02-08 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into adresse (idcontact, adresse, idtype) values ((select max(last_rowid_contact) from _variables), 'adresse C', 1);
insert into adresse (idcontact, adresse, idtype) values ((select max(last_rowid_contact) from _variables), 'Adresse D', 1);
insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'nouvelle Adresse', 0);

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'nouvelle Adresse', 1);

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test2' ,1, 'test2',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'mail C', 1);
insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'Mail D', 1);
insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test3' ,1, 'test3',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'Tel C', 1);

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'Tel D', 1);

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test4' ,1, 'test4',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

drop table _variables;create temp table _variables(last_rowid_contact integer, last_rowid_groupe integer);
insert into groupe (nom) values('nop');
insert into _variables (last_rowid_groupe) values ((select last_insert_rowid() from groupe));

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 't' ,1, 'test',2016-02-08 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into adresse (idcontact, adresse, idtype) values ((select max(last_rowid_contact) from _variables), 'adresse C', 1);
insert into adresse (idcontact, adresse, idtype) values ((select max(last_rowid_contact) from _variables), 'Adresse D', 1);
insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'nouvelle Adresse', 0);

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'nouvelle Adresse', 1);

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test2' ,1, 'test2',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'mail C', 1);
insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'Mail D', 1);
insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test3' ,1, 'test3',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'Tel C', 1);

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), 'Tel D', 1);

insert into contact(idgroupe, nom,favoris, prenom, ddn, fax) VALUES((select max(last_rowid_groupe) from _variables) , 'test4' ,1, 'test4',3900-02-22 ,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

drop table _variables;