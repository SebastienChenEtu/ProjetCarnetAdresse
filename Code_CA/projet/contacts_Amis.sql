create temp table _variables(last_rowid_contact integer, last_rowid_groupe integer);
insert into groupe (nom) values('Amis');
insert into _variables (last_rowid_groupe) values ((select last_insert_rowid() from groupe));

insert into contact(idgroupe, nom,favoris, prenom, ddn, photo,fax) VALUES((select max(last_rowid_groupe) from _variables) , 'LEROY' ,1, 'Noémie',2016-02-09 ,null,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'mail@yopmail.com', 1);
insert into mail(idcontact, mail, idtype) values ((select max(last_rowid_contact) from _variables) , 'Mail2@yopmail.com', 1);
insert into contact(idgroupe, nom,favoris, prenom, ddn, photo,fax) VALUES((select max(last_rowid_groupe) from _variables) , 'NGO' ,1, 'Kévin',2016-02-09 ,null,'fax' );
insert into _variables (last_rowid_contact) values ((select last_insert_rowid() from contact));

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), '+33 08314314', 1);

insert into telephone(idcontact, telephone, idtype) values ((select max(last_rowid_contact) from _variables), '+33 01456784', 1);

drop table _variables;commit;