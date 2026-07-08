Create or replace function random_string(length integer) returns text as
'
declare
chars text[] := ''{0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z}'';
  result text := '''';
  i integer := 0;
begin
  if length < 0 then
    raise exception ''Impossible de générer un string de 0 caractère'';
end if;
for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
end loop;
return result;
end;
'
LANGUAGE plpgsql;

Create or replace function random_ip() returns text as
'
declare
result text := '''';
ip text = CONCAT(
  TRUNC(RANDOM() * 250 + 2), ''.'' ,
  TRUNC(RANDOM() * 250 + 2), ''.'',
  TRUNC(RANDOM() * 250 + 2), ''.'',
  TRUNC(RANDOM() * 250 + 2)
)::INET;
begin
result := ip;
return result;
end;
'
LANGUAGE plpgsql;

Create or replace function random_numeric_string(length integer) returns text as
'
declare
chars text[] := ''{0,1,2,3,4,5,6,7,8,9}'';
  result text := '''';
  i integer := 0;
begin
  if length < 0 then
    raise exception ''Impossible de générer un string de 0 caractère'';
end if;
for i in 1..length loop
    result := result || chars[1+random()*(array_length(chars, 1)-1)];
end loop;
return result;
end;
'
LANGUAGE plpgsql;

Create or replace function random_int(length integer) returns numeric as
'
declare
result integer := 0;
begin
  if length < 0 then
    raise exception ''Impossible de générer un string de 0 caractère'';
end if;
result = FLOOR((select random()) * length);
return result;
end;
'
LANGUAGE plpgsql;